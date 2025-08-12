package com.farasatnovruzov.movieappcompose.screens.weather.main

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.data.DataOrException
import com.farasatnovruzov.movieappcompose.model.weather.Weather

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()){
    ShowData(mainViewModel)
}

@Composable
fun ShowData(mainViewModel: MainViewModel){
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
//        value = mainViewModel.data.value
        value = mainViewModel.getWeatherData("Baku")
    }.value

    if (weatherData.loading == true){
        CircularProgressIndicator()
    }else if (weatherData.data != null){
//        Text(text ="Main Screen: "+ weatherData.data!!.city.country)
        Text(text ="Main Screen: "+ weatherData.data!!.city.country)
    }
}