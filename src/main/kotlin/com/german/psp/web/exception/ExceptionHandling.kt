package com.german.psp.web.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.german.psp.lib.context.requestIdOrThrow
import com.german.psp.lib.validation.FailedValidationException
import com.german.psp.lib.validation.ReasonFormat
import com.german.psp.platform.properties.AppProperties
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.resource.NoResourceFoundException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebInputException
import java.time.Instant
import kotlin.coroutines.coroutineContext

@ControllerAdvice
class ExceptionHandling(appProps: AppProperties, private val objectMapper: ObjectMapper) {
    private val loggingProps = appProps.logging
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(FailedValidationException::class)
    suspend fun handle(exception: FailedValidationException, exchange: ServerWebExchange) =
        when (exception.failure.format) {
            ReasonFormat.STRING -> badRequestWithDetails(exception.failure.reason, exchange.request)
            ReasonFormat.JSON -> badRequestWithDetails(
                objectMapper.readValue(exception.failure.reason),
                exchange.request
            )
        }

    @ExceptionHandler(ServerWebInputException::class)
    suspend fun handle(exception: ServerWebInputException, exchange: ServerWebExchange) =
        badRequestWithDetails("Request body has invalid structure, please consider api scheme", exchange.request)

    @ExceptionHandler(NoResourceFoundException::class)
    suspend fun handle(exception: NoResourceFoundException, exchange: ServerWebExchange) =
        notFoundException(exchange.request)

    @ExceptionHandler(Exception::class)
    suspend fun handleOtherException(exception: Exception, exchange: ServerWebExchange) = run {
        if (loggingProps.enabled) {
            logger.error(
                "For request(id=${coroutineContext.requestIdOrThrow}) there is an uncaught exception: ",
                exception
            )
        } else {
            exception.printStackTrace()
        }
        unknownServerError(exchange.request)
    }


    private fun badRequestWithDetails(details: Any, request: ServerHttpRequest) =
        ResponseEntity.badRequest().body(errorBody(details, request))

    private fun unknownServerError(request: ServerHttpRequest) =
        ResponseEntity.internalServerError().body(errorBody("Internal Server Error has occurred", request))


    private fun notFoundException(request: ServerHttpRequest) = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        errorBody(
            "Not found", request
        )
    )


    private fun errorBody(details: Any, request: ServerHttpRequest) = mapOf(
        "details" to details,
        "timestamp" to Instant.now().toEpochMilli(),
        "path" to request.path.value()
    )
}