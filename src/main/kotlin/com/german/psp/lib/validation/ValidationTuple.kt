package com.german.psp.lib.validation

import com.german.psp.lib.validation.validators.Validator


data class ValidationTuple<T>(val key: String, val value: T, val validator: Validator<T>) {
    suspend fun validate(): ValidationResult = validator.validate(value)

}

