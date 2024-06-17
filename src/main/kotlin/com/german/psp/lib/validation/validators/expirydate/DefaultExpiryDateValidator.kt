package com.german.psp.lib.validation.validators.expirydate

import com.german.psp.lib.validation.ValidationResult
import com.german.psp.lib.validation.failure
import com.german.psp.lib.validation.success
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DefaultExpiryDateValidator : ExpiryDateValidator {
    private val formatter = DateTimeFormatter.ofPattern("MM/yy")
    private val now: YearMonth
        get() = YearMonth.now()

    override suspend fun validate(input: String): ValidationResult = try {
        val expiryYearMonth = YearMonth.parse(input, formatter)
        if (expiryYearMonth.isAfter(now)) {
            success
        } else {
            failure("card is expired")
        }
    } catch (e: DateTimeParseException) {
        failure("expiryDate has invalid format, must have month/year")
    }


}