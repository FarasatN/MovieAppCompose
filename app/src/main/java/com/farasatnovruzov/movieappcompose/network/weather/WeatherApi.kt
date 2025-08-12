package com.farasatnovruzov.movieappcompose.network.weather

import com.farasatnovruzov.movieappcompose.model.weather.Weather
import com.farasatnovruzov.movieappcompose.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query : String = "Baku",
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = Constants.API_KEY // your api key
    ): Weather


}