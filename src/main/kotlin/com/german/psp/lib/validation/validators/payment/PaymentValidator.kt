package com.german.psp.lib.validation.validators.payment

import com.german.psp.lib.validation.ValidationTuple
import com.german.psp.lib.validation.validators.Validator
import com.german.psp.lib.validation.validators.amount.AmountValidator
import com.german.psp.lib.validation.validators.cardnumber.CardNumberValidator
import com.german.psp.lib.validation.validators.currency.CurrencyValidator
import com.german.psp.lib.validation.validators.cvv.CVVValidator
import com.german.psp.lib.validation.validators.expirydate.ExpiryDateValidator
import com.german.psp.lib.validation.validators.merchantid.MerchantIdValidator
import com.german.psp.messages.PaymentInput

abstract class PaymentValidator(
    protected val cardNumberValidator: CardNumberValidator,
    protected val expiryDateValidator: ExpiryDateValidator,
    protected val cvvValidator: CVVValidator,
    protected val amountValidator: AmountValidator,
    protected val currencyValidator: CurrencyValidator,
    protected val merchantIdValidator: MerchantIdValidator,
) : Validator<PaymentInput> {
    protected fun PaymentInput.toValidationTuples(): List<ValidationTuple<*>> {
        return listOf(
            ValidationTuple("card.number", card.number, cardNumberValidator),
            ValidationTuple("card.expiryDate", card.expiryDate, expiryDateValidator),
            ValidationTuple("card.cvv", card.cvv, cvvValidator),
            ValidationTuple("amount", amount, amountValidator),
            ValidationTuple("currency", currency, currencyValidator),
            ValidationTuple("merchantId", merchantId, merchantIdValidator)
        )
    }
}