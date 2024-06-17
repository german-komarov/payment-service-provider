package com.german.psp.lib.context

import com.german.psp.lib.context.ReactorContextKeys.REQUEST_ID
import kotlinx.coroutines.reactor.ReactorContext
import kotlin.coroutines.CoroutineContext

val CoroutineContext.requestId
    get() = get(ReactorContext)?.context?.get<String>(REQUEST_ID)

val CoroutineContext.requestIdOrThrow
    get() = requestId ?: throw IllegalStateException("No $REQUEST_ID was found in the context")