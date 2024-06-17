package com.german.psp.messages

data class AcquirerResponse(val isApproved: Boolean, val details: Map<String, Any>? = null)