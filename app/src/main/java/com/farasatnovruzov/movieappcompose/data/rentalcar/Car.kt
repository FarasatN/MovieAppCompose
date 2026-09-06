package com.farasatnovruzov.movieappcompose.data.rentalcar

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.farasatnovruzov.movieappcompose.R

data class Car(
    val name: String,
    @DrawableRes val image: Int,
    val color: Color,
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
        color = Color.Black,
        logo = R.drawable.baseline_star_24,
        recommendation = 98,
        recommendationRate = 4.8f,
        rentalDays = 7,
        price = 450,
        recommenders = listOf(R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24),
        bgColor = Color.LightGray
    ),
    Car(
        name = "BMW M2 Competition",
        image = R.drawable.bmw_white,
        color = Color.White,
        logo = R.drawable.baseline_star_24,
        recommendation = 94,
        recommendationRate = 4.8f,
        rentalDays = 7,
        price = 450,
        recommenders = listOf(R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24),
        bgColor = Color.LightGray
    ),
    Car(
        name = "TMitsubishi ASX",
        image = R.drawable.mitsubishi_red,
        color = Color.Red,
        logo = R.drawable.baseline_star_24,
        recommendation = 87,
        recommendationRate = 4.8f,
        rentalDays = 7,
        price = 450,
        recommenders = listOf(R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24, R.drawable.baseline_person_2_24),
        bgColor = Color.LightGray
    )
)