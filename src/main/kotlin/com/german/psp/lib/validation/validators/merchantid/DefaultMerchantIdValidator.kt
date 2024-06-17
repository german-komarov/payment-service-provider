package com.german.psp.lib.validation.validators.merchantid

import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.failure
import com.german.psp.lib.validation.success

object DefaultMerchantIdValidator : MerchantIdValidator {
    override suspend fun validate(input: String): ValidationResult = if (input.isBlank()) {
        failure("merchant's id cannot be empty or blank")
    } else {
        success
    }

}