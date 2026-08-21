package com.farasatnovruzov.movieappcompose.data.rentalcar

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.farasatnovruzov.movieappcompose.R
import com.google.android.gms.common.util.CollectionUtils.listOf

data class Car(
    val name: String,
    @DrawableRes val image: Int,
    val color: String,
    @DrawableRes val logo : Int,
    val recommendation: Int,
    val recommendationRate: Float,
    val rentalDays: Int,
    val price: Int,
    val recommenders: List<Int>,
    val bgColor: Color
)

val luxuriousCars = listOf(
    Car(
        name = "Tesla Model S",
        image = R.drawable.tesla_black,
        color = "Black",
        logo = R.drawable.baseline_star_24,
        recommendation = 1240,
        recommendationRate = 4.8f,
        rentalDays = 7,
        price = 450,
        recommenders = listOf(R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24),
        bgColor = Color(0xFF1E1E1E)
    ),
    Car(
        name = "BMW M2 Competition",
        image = R.drawable.bmw_white,
        color = "White",
        logo = R.drawable.baseline_star_24,
        recommendation = 1240,
        recommendationRate = 4.8f,
        rentalDays = 7,
        price = 450,
        recommenders = listOf(R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24),
        bgColor = Color(0xFF1E1E1E)
    ),
    Car(
        name = "TMitsubishi ASX / Outlander Sport",
        image = R.drawable.mitsubishi_red,
        color = "Red",
        logo = R.drawable.baseline_star_24,
        recommendation = 1240,
        recommendationRate = 4.8f,
        rentalDays = 7,
        price = 450,
        recommenders = listOf(R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24),
        bgColor = Color(0xFF1E1E1E)
    )
)