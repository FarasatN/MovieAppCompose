package com.farasatnovruzov.movieappcompose.screens.weather.main

import androidx.lifecycle.ViewModel
import com.farasatnovruzov.movieappcompose.data.DataOrException
import com.farasatnovruzov.movieappcompose.model.weather.remote.Weather
import com.farasatnovruzov.movieappcompose.repository.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    suspend fun getWeatherData(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }


//    val data: MutableState<DataOrException<Weather, Boolean, Exception>>
//    = mutableStateOf(DataOrException(null, true, Exception("")))
//
//    init {
//        loadWeather()
//    }
//
//    private fun loadWeather(){
//        getWeather("Tokio")
//    }
//
//    private fun getWeather(city: String) {
//        viewModelScope.launch {
//            if (city.isEmpty()) return@launch
//            data.value.loading = true
//            data.value = repository.getWeather(cityQuery = city)
//            Log.d("GET", "getWeather: ${data.value.data.toString()}")
//            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
//        }
//        Log.d("GET", "getWeather: ${data.value.data.toString()}")
//
//    }

}