package com.german.psp.lib.validation.validators.currency

import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.failure
import com.german.psp.lib.validation.success

class SupplierBasedCurrencyValidator(availableCurrenciesSupplier: suspend () -> Iterable<String>) : CurrencyValidator {

    private val availableCurrenciesSupplier = suspend {
        availableCurrenciesSupplier().map { it.lowercase() }.toHashSet()
    }

    override suspend fun validate(input: String): ValidationResult =
        if (input.isBlank()) {
            failure("currency must not be blank or empty")
        } else if (availableCurrenciesSupplier().contains(input.lowercase())) {
            success
        } else {
            failure("$input is not supported currency")
        }

}