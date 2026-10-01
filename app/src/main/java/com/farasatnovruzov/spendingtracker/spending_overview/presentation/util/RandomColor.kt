package com.farasatnovruzov.spendingtracker.spending_overview.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.random.Random

fun randomColor(seed: Int = 0, minBrightness: Int = 90): Int {
    val random = if (seed != 0) Random(seed) else Random.Default
    val red = random.nextInt(minBrightness, 256)
    val green = random.nextInt(minBrightness, 256)
    val blue = random.nextInt(minBrightness, 256)
    return Color(red, green, blue).copy(0.3f).toArgb()
}