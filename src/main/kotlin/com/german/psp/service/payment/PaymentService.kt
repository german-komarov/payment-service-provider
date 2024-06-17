package com.german.psp.service.payment

import com.german.psp.messages.PaymentInput
import com.german.psp.messages.PaymentResult

interface PaymentService {
    suspend fun processPayment(input: PaymentInput): PaymentResult
}