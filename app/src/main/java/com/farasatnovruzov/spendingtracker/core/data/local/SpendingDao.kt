package com.farasatnovruzov.spendingtracker.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SpendingDao {

    @Upsert
    suspend fun upsertSpending(entity: SpendingEntity)

    // Flow qaytarır: cədvəl dəyişən kimi Room özü yeni siyahı göndərir
    @Query("SELECT * FROM spendingentity ORDER BY dateTimeUtc DESC")
    fun observeAllSpendings(): Flow<List<SpendingEntity>>

    @Query("SELECT * FROM spendingentity WHERE spendingId = :id")
    suspend fun getSpending(id: Int): SpendingEntity?

    // Qeyd: price xərcin ÜMUMİ məbləğidir (quantity ilə vurulmur)
    @Query("SELECT SUM(price) FROM spendingentity")
    fun observeTotalSpent(): Flow<Double?>

    @Query("DELETE FROM spendingentity WHERE spendingId = :id")
    suspend fun deleteSpending(id: Int)
}
