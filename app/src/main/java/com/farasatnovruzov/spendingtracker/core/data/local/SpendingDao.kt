package com.farasatnovruzov.spendingtracker.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SpendingDao {

    @Upsert
    suspend fun upsertSpending(entity: SpendingEntity)

    @Query("SELECT * FROM spendingentity ORDER BY dateTimeUtc DESC")
    suspend fun getAllSpendings(): List<SpendingEntity>

    @Query("SELECT * FROM spendingentity WHERE substr(dateTimeUtc, 1, 10) = :dateIso ORDER BY dateTimeUtc DESC")
    suspend fun getSpendingsByDate(dateIso: String): List<SpendingEntity>

    @Query("SELECT * FROM spendingentity WHERE spendingId = :id")
    suspend fun getSpending(id: Int): SpendingEntity?

    @Query("SELECT DISTINCT substr(dateTimeUtc, 1, 10) FROM spendingentity ORDER BY dateTimeUtc DESC")
    suspend fun getAllUniqueDates(): List<String>

    @Query("SELECT SUM(price) FROM spendingentity")
    suspend fun getSpendBalance(): Double?

    @Query("DELETE FROM spendingentity WHERE spendingId = :id")
    suspend fun deleteSpending(id: Int)
}