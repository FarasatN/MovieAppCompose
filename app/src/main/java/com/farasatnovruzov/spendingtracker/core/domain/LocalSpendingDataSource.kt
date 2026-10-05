package com.farasatnovruzov.spendingtracker.core.domain

import kotlinx.coroutines.flow.Flow

interface LocalSpendingDataSource {
    fun observeAllSpendings(): Flow<List<Spending>>
    fun observeTotalSpent(): Flow<Double>
    suspend fun upsertSpending(spending: Spending)
    suspend fun getSpending(id: Int): Spending?
    suspend fun deleteSpending(id: Int)
}
