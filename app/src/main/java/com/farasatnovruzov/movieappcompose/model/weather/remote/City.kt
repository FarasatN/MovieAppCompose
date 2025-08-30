package com.farasatnovruzov.movieappcompose.model.weather.remote

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)