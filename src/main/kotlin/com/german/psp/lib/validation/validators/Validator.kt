package com.german.psp.lib.validation.validators

import com.german.psp.lib.validation.ValidationResult

interface Validator<I> {
    // Function is marked "suspend" because some validators may involve suspendable calls
    suspend fun validate(input: I): ValidationResult
}