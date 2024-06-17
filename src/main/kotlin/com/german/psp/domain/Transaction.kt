package com.german.psp.domain


interface IsTerminalStatus {
    val isTerminal: Boolean
}

enum class TransactionStatus : IsTerminalStatus {
    PENDING {
        override val isTerminal: Boolean = false
    },
    APPROVED {
        override val isTerminal: Boolean = true
    },
    DENIED {
        override val isTerminal: Boolean = true
    }
}

class Transaction(
    val id: String,
    status: TransactionStatus,
    val merchantId: String,
    val amount: Long,
    val currency: String,
    val card: Card
) {
    var status = status
        private set


    fun updateStatus(newStatus: TransactionStatus) {
        if (this.status.isTerminal) {
            throw IllegalStatusUpdate("It is forbidden to change terminal status")
        }

        this.status = newStatus
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transaction

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}


data class IllegalStatusUpdate(override val message: String) : Exception()