package com.farasatnovruzov.spendingtracker.core.domain

import java.time.ZonedDateTime

interface LocalSpendingDataSource {
    suspend fun getAllSpendings(): List<Spending>
    suspend fun getAllSpendingsByDate(dateTimeUtc: ZonedDateTime): List<Spending>
    suspend fun getAllDates(): List<ZonedDateTime>
    suspend fun upsertSpending(spending: Spending)
    suspend fun getSpending(id: Int): Spending?
    suspend fun getSpendBalance(): Double?
    suspend fun deleteSpending(id: Int)
}