package com.german.psp.service.acquirer.b

import com.german.psp.domain.Transaction
import com.german.psp.messages.AcquirerResponse
import com.german.psp.service.acquirer.mockSendingTransactionLogin

class MockBAcquirerService : BAcquirerService {
    override suspend fun sendTransaction(transaction: Transaction): AcquirerResponse =
        mockSendingTransactionLogin(transaction)
}