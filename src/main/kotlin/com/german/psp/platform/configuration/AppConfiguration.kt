package com.german.psp.platform.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.german.psp.platform.properties.AppProperties
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.concurrent.Executors

@Configuration
@EnableConfigurationProperties(AppProperties::class)
class AppConfiguration {
    @Bean
    @Primary
    fun appObjectMapper(): ObjectMapper = jacksonObjectMapper()


    @Bean
    fun virtualThreadDispatcher(): ExecutorCoroutineDispatcher =
        Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()
}