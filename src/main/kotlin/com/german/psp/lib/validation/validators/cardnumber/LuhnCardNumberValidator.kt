package com.german.psp.lib.validation.validators.cardnumber

import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.failure
import com.german.psp.lib.validation.success
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit.LUHN_CHECK_DIGIT

object LuhnCardNumberValidator : CardNumberValidator {
    override suspend fun validate(input: String): ValidationResult = if (LUHN_CHECK_DIGIT.isValid(input)) {
        success
    } else {
        failure("Card number is not valid")
    }
}