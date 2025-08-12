package com.farasatnovruzov.movieappcompose.repository.weather

import android.util.Log
import com.farasatnovruzov.movieappcompose.data.DataOrException
import com.farasatnovruzov.movieappcompose.model.weather.Weather
import com.farasatnovruzov.movieappcompose.network.weather.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String)
    : DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        }catch (e: Exception){
            Log.d("EXCEPTION", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("WEATHER", "getWeather: $response")
        return DataOrException(data = response)
    }



}