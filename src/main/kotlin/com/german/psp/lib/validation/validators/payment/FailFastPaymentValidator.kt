package com.german.psp.lib.validation.validators.payment

import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.success
import com.german.psp.lib.validation.validators.amount.AmountValidator
import com.german.psp.lib.validation.validators.cardnumber.CardNumberValidator
import com.german.psp.lib.validation.validators.currency.CurrencyValidator
import com.german.psp.lib.validation.validators.cvv.CVVValidator
import com.german.psp.lib.validation.validators.expirydate.ExpiryDateValidator
import com.german.psp.lib.validation.validators.merchantid.MerchantIdValidator
import com.german.psp.messages.PaymentInput

class FailFastPaymentValidator(
    cardNumberValidator: CardNumberValidator,
    expiryDateValidator: ExpiryDateValidator,
    cvvValidator: CVVValidator,
    amountValidator: AmountValidator,
    currencyValidator: CurrencyValidator,
    merchantIdValidator: MerchantIdValidator,
) : PaymentValidator(
    cardNumberValidator,
    expiryDateValidator,
    cvvValidator,
    amountValidator,
    currencyValidator,
    merchantIdValidator
) {
    override suspend fun validate(input: PaymentInput): ValidationResult = run {
        input.toValidationTuples().forEach { tuple ->
            tuple.validate().also { result ->
                if (result is ValidationResult.Failure) {
                    return@run result
                }
            }
        }
        success
    }


}