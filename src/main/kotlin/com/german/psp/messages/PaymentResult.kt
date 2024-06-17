package com.german.psp.messages

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.german.psp.domain.TransactionStatus

data class PaymentResult(
    @JsonProperty("transaction_id") val transactionId: String,
    @JsonProperty("transaction_status") val transactionStatus: TransactionStatus,
    @JsonProperty("details") @JsonInclude(JsonInclude.Include.NON_ABSENT) val details: Map<String, Any>? = null
)