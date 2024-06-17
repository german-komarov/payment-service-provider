package com.german.psp.service.acquirer.a

import com.german.psp.domain.Transaction
import com.german.psp.messages.AcquirerResponse
import com.german.psp.service.acquirer.mockSendingTransactionLogin

class MockAAcquirerService : AAcquirerService {
    override suspend fun sendTransaction(transaction: Transaction): AcquirerResponse =
        mockSendingTransactionLogin(transaction)
}