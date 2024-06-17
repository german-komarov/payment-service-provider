package com.german.psp.service.acquirer

import com.german.psp.domain.Transaction
import com.german.psp.messages.AcquirerResponse

fun mockSendingTransactionLogin(transaction: Transaction): AcquirerResponse =
    if ((transaction.card.number.last().digitToInt() % 2) == 0) {
        AcquirerResponse(true)
    } else {
        AcquirerResponse(false)
    }