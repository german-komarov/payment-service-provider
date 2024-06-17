package com.german.psp.platform.configuration.acquirer

import com.german.psp.service.acquirer.a.AAcquirerService
import com.german.psp.service.acquirer.a.MockAAcquirerService
import com.german.psp.service.acquirer.b.BAcquirerService
import com.german.psp.service.acquirer.b.MockBAcquirerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AcquirerServiceConfiguration {
    @Bean
    fun aAcquirerService(): AAcquirerService = MockAAcquirerService()

    @Bean
    fun bAcquirerService(): BAcquirerService = MockBAcquirerService()
}