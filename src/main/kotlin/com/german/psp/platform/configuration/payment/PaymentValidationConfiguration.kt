package com.german.psp.platform.configuration.payment

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.german.psp.lib.validation.validators.amount.AmountValidator
import com.german.psp.lib.validation.validators.amount.DefaultAmountValidator
import com.german.psp.lib.validation.validators.cardnumber.CardNumberValidator
import com.german.psp.lib.validation.validators.cardnumber.LuhnCardNumberValidator
import com.german.psp.lib.validation.validators.currency.CacheBasedCurrencyValidator
import com.german.psp.lib.validation.validators.currency.CurrencyValidator
import com.german.psp.lib.validation.validators.cvv.CVVValidator
import com.german.psp.lib.validation.validators.cvv.DefaultCVVValidator
import com.german.psp.lib.validation.validators.expirydate.DefaultExpiryDateValidator
import com.german.psp.lib.validation.validators.expirydate.ExpiryDateValidator
import com.german.psp.lib.validation.validators.merchantid.DefaultMerchantIdValidator
import com.german.psp.lib.validation.validators.merchantid.MerchantIdValidator
import com.german.psp.lib.validation.validators.payment.ComposingPaymentValidator
import com.german.psp.lib.validation.validators.payment.FailFastPaymentValidator
import com.german.psp.lib.validation.validators.payment.PaymentValidator
import com.german.psp.platform.properties.PaymentProperties
import com.german.psp.platform.properties.PaymentValidationStrategy.COMPOSING
import com.german.psp.platform.properties.PaymentValidationStrategy.FAILFAST
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaymentValidationConfiguration(
    private val props: PaymentProperties
) {

    @Bean
    fun cardNumberValidator(): CardNumberValidator = LuhnCardNumberValidator

    @Bean
    fun expiryDateValidator(): ExpiryDateValidator = DefaultExpiryDateValidator

    @Bean
    fun cvvValidator(): CVVValidator = DefaultCVVValidator

    @Bean
    fun amountValidator(): AmountValidator = DefaultAmountValidator

    @Bean
    fun currencyValidator(): CurrencyValidator = CacheBasedCurrencyValidator(props.currencies.available)

    @Bean
    fun merchantIdValidator(): MerchantIdValidator = DefaultMerchantIdValidator

    @Bean
    fun validationObjectMapper(): ObjectMapper = jacksonObjectMapper()


    // It is possible to use @ConditionalOnProperty, but then it will be required to copy-paste this many-parameters
    // function for each possible value of enum, so I decided to make it via "when" construction
    @Bean
    fun paymentValidator(
        cardNumberValidator: CardNumberValidator,
        expiryDateValidator: ExpiryDateValidator,
        cvvValidator: CVVValidator,
        amountValidator: AmountValidator,
        currencyValidator: CurrencyValidator,
        merchantIdValidator: MerchantIdValidator,
        @Qualifier("validationObjectMapper") objectMapper: ObjectMapper
    ): PaymentValidator = when (props.validation.strategy) {
        FAILFAST -> FailFastPaymentValidator(
            cardNumberValidator,
            expiryDateValidator,
            cvvValidator,
            amountValidator,
            currencyValidator,
            merchantIdValidator
        )

        COMPOSING -> ComposingPaymentValidator(
            cardNumberValidator,
            expiryDateValidator,
            cvvValidator,
            amountValidator,
            currencyValidator,
            merchantIdValidator
        ) {
            objectMapper.writeValueAsString(it)
        }
    }
}