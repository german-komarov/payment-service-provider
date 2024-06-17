package com.german.psp.platform.configuration.payment

import com.german.psp.dao.transaction.TransactionRepository
import com.german.psp.service.acquirer.a.AAcquirerService
import com.german.psp.service.acquirer.b.BAcquirerService
import com.german.psp.service.payment.NonTransactionalPaymentService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaymentServiceConfiguration {
    @Bean
    fun paymentService(
        transactionRepository: TransactionRepository,
        aAcquirerService: AAcquirerService,
        bAcquirerService: BAcquirerService
    ) = NonTransactionalPaymentService(transactionRepository, aAcquirerService, bAcquirerService)

}