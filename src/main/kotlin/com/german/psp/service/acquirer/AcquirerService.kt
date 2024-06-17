package com.german.psp.service.acquirer

import com.german.psp.domain.Transaction
import com.german.psp.messages.AcquirerResponse

interface AcquirerService {
    suspend fun sendTransaction(transaction: Transaction): AcquirerResponse
}