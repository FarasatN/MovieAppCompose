package com.farasatnovruzov.spendingtracker.spending_overview.presentation

import com.farasatnovruzov.spendingtracker.core.domain.Spending
import java.time.ZonedDateTime

data class SpendingOverviewState(
    val spendingList: List<Spending> = emptyList(),
    val datesList: List<ZonedDateTime> = emptyList(),
    val balance: Double = 0.0,
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val isDropDownMenuVisible: Boolean = false
)
