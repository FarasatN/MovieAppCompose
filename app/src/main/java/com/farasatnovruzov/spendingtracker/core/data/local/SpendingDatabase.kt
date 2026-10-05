package com.farasatnovruzov.spendingtracker.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [SpendingEntity::class],
    version = 1,
    exportSchema = true
)
abstract class SpendingDatabase: RoomDatabase() {
    abstract val dao : SpendingDao
}
