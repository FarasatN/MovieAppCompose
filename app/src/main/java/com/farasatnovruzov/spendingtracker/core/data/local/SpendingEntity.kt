package com.farasatnovruzov.spendingtracker.core.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["dateTimeUtc"])]
)
data class SpendingEntity(
    @PrimaryKey(autoGenerate = true)
    val spendingId: Int? = null,
    val name: String,
    val price: Double,
    val kilograms: Double,
    val quantity: Double,
    val dateTimeUtc: String,
)