package com.german.psp.dao.transaction

import com.german.psp.domain.Transaction
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

/**
 * [executorCoroutineDispatcher] is used to deal with blocking nature of [ConcurrentHashMap] structure of [storage]
 */
class InMemoryTransactionRepository(
    private val executorCoroutineDispatcher: ExecutorCoroutineDispatcher
) : TransactionRepository {

    private val storage: ConcurrentHashMap<String, Transaction> = ConcurrentHashMap()

    override suspend fun findTransaction(id: String): Transaction? = coroutineScope {
        async(executorCoroutineDispatcher) {
            storage[id]
        }.await()
    }

    override suspend fun saveTransaction(transaction: Transaction) = coroutineScope {
        launch(executorCoroutineDispatcher) { storage[transaction.id] = transaction }.join()
        transaction
    }
}