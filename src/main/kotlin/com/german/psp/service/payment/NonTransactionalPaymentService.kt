package com.german.psp.service.payment

import com.german.psp.dao.transaction.TransactionRepository
import com.german.psp.domain.Card
import com.german.psp.domain.Transaction
import com.german.psp.domain.TransactionStatus
import com.german.psp.lib.sumOfDigits
import com.german.psp.messages.AcquirerResponse
import com.german.psp.messages.PaymentInput
import com.german.psp.messages.PaymentResult
import com.german.psp.service.acquirer.a.AAcquirerService
import com.german.psp.service.acquirer.b.BAcquirerService
import java.util.*

class NonTransactionalPaymentService(
    private val transactionRepository: TransactionRepository,
    private val aAcquirerService: AAcquirerService,
    private val bAcquirerService: BAcquirerService
) : PaymentService {
    override suspend fun processPayment(input: PaymentInput): PaymentResult {
        val transaction = input.run {
            Transaction(
                UUID.randomUUID().toString(),
                TransactionStatus.PENDING,
                merchantId,
                amount,
                currency,
                Card(card.number, card.expiryDate, card.cvv)
            )
        }

        transactionRepository.saveTransaction(transaction)

        val bin = transaction.card.bin
        val isSumOfBinDigitsEven = bin.sumOfDigits.let { (it % 2) == 0 }
        val acquirerResponse: AcquirerResponse = if (isSumOfBinDigitsEven) {
            aAcquirerService.sendTransaction(transaction)
        } else {
            bAcquirerService.sendTransaction(transaction)
        }

        if(acquirerResponse.isApproved) {
            transaction.updateStatus(TransactionStatus.APPROVED)
        } else {
            transaction.updateStatus(TransactionStatus.DENIED)
        }

        return PaymentResult(transaction.id, transaction.status, acquirerResponse.details)
    }


}