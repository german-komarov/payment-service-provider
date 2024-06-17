package com.german.psp.platform.properties

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties("com.german.psp.payment")
data class PaymentProperties(
    val validation: PaymentValidationProperties,
    val currencies: PaymentCurrenciesProperties
)


enum class PaymentValidationStrategy {
    FAILFAST,
    COMPOSING
}

data class PaymentValidationProperties(
    val strategy: PaymentValidationStrategy
)


data class PaymentCurrenciesProperties(
    val available: List<String>
)