package com.german.psp.platform.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("com.german.psp.app")
data class AppProperties(val logging: Logging)


data class Logging(val enabled: Boolean)