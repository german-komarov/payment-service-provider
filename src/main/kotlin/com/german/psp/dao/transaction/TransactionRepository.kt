package com.german.psp.dao.transaction

import com.german.psp.domain.Transaction

interface TransactionRepository {
    suspend fun findTransaction(id: String): Transaction?
    suspend fun saveTransaction(transaction: Transaction): Transaction
}