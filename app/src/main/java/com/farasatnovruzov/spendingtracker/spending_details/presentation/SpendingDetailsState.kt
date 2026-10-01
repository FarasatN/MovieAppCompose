package com.farasatnovruzov.spendingtracker.spending_details.presentation

import androidx.compose.runtime.Immutable
import java.time.ZonedDateTime

@Immutable
data class SpendingDetailsState(
    val spendingId: Int? = null,
    val name: String = "",
    val price: Double = 0.0,
    val kilograms: Double = 0.0,
    val quantity: Double = 0.0,
    val dateTimeUtc: ZonedDateTime? = null,
)