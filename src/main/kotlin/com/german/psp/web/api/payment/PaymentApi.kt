package com.german.psp.web.api.payment

import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.toException
import com.german.psp.lib.validation.validators.payment.PaymentValidator
import com.german.psp.messages.PaymentInput
import com.german.psp.messages.PaymentResult
import com.german.psp.service.payment.PaymentService
import com.german.psp.web.api.payment.PaymentApiRouting.BASE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


object PaymentApiRouting {
    const val BASE = "api/v1/payments"
}


@RestController
@RequestMapping("/$BASE")
class PaymentApi(
    private val paymentValidator: PaymentValidator,
    private val paymentService: PaymentService
) {

    @PostMapping
    suspend fun processPayment(@RequestBody paymentInput: PaymentInput): PaymentResult {
        paymentValidator.validate(paymentInput)
            .also { if (it is ValidationResult.Failure) throw it.toException() }

        return paymentService.processPayment(paymentInput)
    }
}