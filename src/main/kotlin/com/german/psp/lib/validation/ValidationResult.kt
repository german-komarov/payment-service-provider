package com.german.psp.lib.validation


enum class ReasonFormat {
    JSON, STRING
}


sealed interface ValidationResult {
    data object Success : ValidationResult
    data class Failure(val reason: String, val format: ReasonFormat) : ValidationResult
}

fun ValidationResult.Failure.toException() = FailedValidationException(this)


data class FailedValidationException(val failure: ValidationResult.Failure) : Exception()

val success = ValidationResult.Success
fun failure(reason: String) = ValidationResult.Failure(reason, ReasonFormat.STRING)
inline fun <reified T> failureConvertingToJson(data: T, converter: (T) -> String) =
    ValidationResult.Failure(converter(data), ReasonFormat.JSON)