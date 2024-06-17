package com.german.psp.platform.configuration.payment

import com.german.psp.platform.properties.PaymentProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(PaymentProperties::class)
class PaymentCommonConfiguration