package com.german.psp.platform.configuration.transaction

import com.german.psp.dao.transaction.InMemoryTransactionRepository
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TransactionDAOConfiguration {
    @Bean
    fun transactionRepository(@Qualifier("virtualThreadDispatcher") virtualThreadDispatcher: ExecutorCoroutineDispatcher) =
        InMemoryTransactionRepository(virtualThreadDispatcher)
}