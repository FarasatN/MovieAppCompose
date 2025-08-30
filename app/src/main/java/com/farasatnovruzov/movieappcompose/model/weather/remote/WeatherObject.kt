package com.farasatnovruzov.movieappcompose.model.weather.remote

data class WeatherObject(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)