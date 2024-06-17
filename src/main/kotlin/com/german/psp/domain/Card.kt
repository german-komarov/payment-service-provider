package com.german.psp.domain

class Card(
    val number: String,
    val expireDate: String,
    val cvv: String
) {
    val bin
        get() = number.substring(0, 6)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        return number == other.number
    }

    override fun hashCode(): Int {
        return number.hashCode()
    }
}