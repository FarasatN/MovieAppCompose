package com.farasatnovruzov.spendingtracker.core.domain

interface CoreRepository {
    suspend fun updateBalance(balance: Double)
    suspend fun getBalance(): Double
}