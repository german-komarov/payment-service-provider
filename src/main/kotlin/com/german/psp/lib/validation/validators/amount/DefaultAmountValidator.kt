package com.german.psp.lib.validation.validators.amount

import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.failure
import com.german.psp.lib.validation.success

object DefaultAmountValidator : AmountValidator {
    override suspend fun validate(input: Long): ValidationResult = try {
        if (input <= 0) {
            failure("Amount must be valid integer number of currency subunits (eg cents) greater than 0")
        } else {
            success
        }
    } catch (e: NumberFormatException) {
        failure("Amount must be valid integer number")
    }
}