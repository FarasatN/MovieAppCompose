package com.farasatnovruzov.spendingtracker.core.domain

import kotlinx.coroutines.flow.Flow

interface CoreRepository {
    fun observeBalance(): Flow<Double>
    suspend fun updateBalance(balance: Double)
    suspend fun getBalance(): Double
}
