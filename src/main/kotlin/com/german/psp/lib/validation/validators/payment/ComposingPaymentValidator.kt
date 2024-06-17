package com.german.psp.lib.validation.validators.payment

import com.german.psp.lib.appendValue
import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.failureConvertingToJson
import com.german.psp.lib.validation.success
import com.german.psp.lib.validation.validators.amount.AmountValidator
import com.german.psp.lib.validation.validators.cardnumber.CardNumberValidator
import com.german.psp.lib.validation.validators.currency.CurrencyValidator
import com.german.psp.lib.validation.validators.cvv.CVVValidator
import com.german.psp.lib.validation.validators.expirydate.ExpiryDateValidator
import com.german.psp.lib.validation.validators.merchantid.MerchantIdValidator
import com.german.psp.messages.PaymentInput

class ComposingPaymentValidator(
    cardNumberValidator: CardNumberValidator,
    expiryDateValidator: ExpiryDateValidator,
    cvvValidator: CVVValidator,
    amountValidator: AmountValidator,
    currencyValidator: CurrencyValidator,
    merchantIdValidator: MerchantIdValidator,
    private val toJson: (Any) -> String
) : PaymentValidator(
    cardNumberValidator,
    expiryDateValidator,
    cvvValidator,
    amountValidator,
    currencyValidator,
    merchantIdValidator
) {
    override suspend fun validate(input: PaymentInput): ValidationResult = run {
        val failures = mutableMapOf<String, MutableList<String>>()

        input.toValidationTuples().forEach { tuple ->
            tuple.validate().also { result ->
                if (result is ValidationResult.Failure) {
                    failures.appendValue(tuple.key, result.reason)
                }
            }
        }

        return@run if (failures.isEmpty()) {
            success
        } else {
            failureConvertingToJson(failures, toJson)
        }
    }


}