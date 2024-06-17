package com.german.psp.web.filters

import com.german.psp.lib.context.ReactorContextKeys.REQUEST_ID
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

@Component
class RequestIdWebFilter : WebFilter {
    val headerKey = "X-Request-ID"
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val headers: HttpHeaders = exchange.request.headers
        val requestId: String = headers[headerKey]?.toString() ?: UUID.randomUUID().toString()
        exchange.response.headers[headerKey] = requestId
        return chain.filter(exchange).contextWrite { context ->
            context.put(REQUEST_ID, requestId)
        }
    }
}