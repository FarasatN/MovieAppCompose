package com.farasatnovruzov.spendingtracker.spending_overview.presentation.util

import java.time.ZonedDateTime


fun ZonedDateTime.formatDate(): String{
    return "$dayOfMonth-$monthValue-$year"
}