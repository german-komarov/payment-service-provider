package com.german.psp.lib.validation.validators.cvv

import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.failure
import com.german.psp.lib.validation.success

object DefaultCVVValidator : CVVValidator {
    override suspend fun validate(input: String): ValidationResult = try {
        val cvvNumber = input.toInt()
        if (cvvNumber in 100..9999) {
            success
        } else {
            failure("cvv must be number in [100, 9999] range")
        }
    } catch (e: NumberFormatException) {
        failure("cvv is not valid integer")
    }
}