package com.german.psp

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(
    title = "Payment Service Provider",
    version = "1.0",
    description = "API documentation"
))
class PspApplication

fun main(args: Array<String>) {
    runApplication<PspApplication>(*args)
}
