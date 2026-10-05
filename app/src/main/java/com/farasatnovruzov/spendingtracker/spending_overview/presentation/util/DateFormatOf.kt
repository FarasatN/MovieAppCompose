package com.farasatnovruzov.spendingtracker.spending_overview.presentation.util

import java.time.LocalDate


fun LocalDate.formatDate(): String {
    return "$dayOfMonth-$monthValue-$year"
}
