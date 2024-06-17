package com.german.psp.messages

import com.fasterxml.jackson.annotation.JsonProperty


data class CardInput(
    @JsonProperty("number") val number: String,
    @JsonProperty("expiry_date") val expiryDate: String,
    @JsonProperty("cvv") val cvv: String
)


data class PaymentInput(
    @JsonProperty("card") val card: CardInput,
    @JsonProperty("amount") val amount: Long,
    @JsonProperty("currency") val currency: String,
    @JsonProperty("merchant_id") val merchantId: String
)
