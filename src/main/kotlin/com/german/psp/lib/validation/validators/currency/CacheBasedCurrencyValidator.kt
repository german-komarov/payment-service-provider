package com.german.psp.lib.validation.validators.currency

import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.failure
import com.german.psp.lib.validation.success

class CacheBasedCurrencyValidator(availableCurrencies: Iterable<String>) : CurrencyValidator {
    private val availableCurrencies = availableCurrencies.map { it.lowercase() }.toHashSet()

    override suspend fun validate(input: String): ValidationResult =
        if (input.isBlank()) {
            failure("currency must not be blank or empty")
        } else if(availableCurrencies.contains(input.lowercase())) {
            success
        } else {
            failure("$input is not supported currency")
        }

}