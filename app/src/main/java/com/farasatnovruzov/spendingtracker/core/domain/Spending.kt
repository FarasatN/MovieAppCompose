package com.farasatnovruzov.spendingtracker.core.domain

import androidx.compose.runtime.Immutable
import java.time.ZonedDateTime

@Immutable
data class Spending(
    val spendingId: Int?,
    val name: String,
    val price: Double,
    val kilograms: Double,
    val quantity: Double,
    val dateTimeUtc: ZonedDateTime
)
