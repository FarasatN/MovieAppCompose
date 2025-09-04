package com.farasatnovruzov.movieappcompose.screens.weather.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.data.DataOrException
import com.farasatnovruzov.movieappcompose.model.weather.remote.Weather
import com.farasatnovruzov.movieappcompose.navigation.weather.WeatherScreens
import com.farasatnovruzov.movieappcompose.screens.weather.settings.SettingsViewModel
import com.farasatnovruzov.movieappcompose.utils.formatDate
import com.farasatnovruzov.movieappcompose.widgets.weather.GetWeatherAnimatedBrush
import com.farasatnovruzov.movieappcompose.widgets.weather.HumidityWindPressureRow
import com.farasatnovruzov.movieappcompose.widgets.weather.SunsetSunRiseRow
import com.farasatnovruzov.movieappcompose.widgets.weather.WeatherAppBar
import com.farasatnovruzov.movieappcompose.widgets.weather.WeatherStateImage

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel(), settingsViewModel: SettingsViewModel = hiltViewModel(), city: String?) {
    val curCity: String = if (city!!.isBlank()) "Baku" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)) {
            value = mainViewModel.getWeatherData(city = curCity,
                units = unit)
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        }else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, navController,
                isImperial = isImperial)

        }

    }

}

@Composable
fun MainScaffold(weather: Weather, navController: NavController,isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + ",${weather.city.country}",
//            icon = Icons.AutoMirrored.Filled.ArrowBack,
            elevation = 25.dp,
            padding = 0.dp,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            navController = navController
        )
    }) { paddingValues ->
        MainContent(data = weather, paddingValues, isImperial)
    }

}

@Composable
fun MainContent(data: Weather, paddingValues: PaddingValues = PaddingValues(), isImperial: Boolean) {
    val today = data.list[0]
    val todayImageUrl = "https://openweathermap.org/img/wn/${today.weather[0].icon}.png"
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatDate((today.dt).toLong()),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        val animatedBrush = GetWeatherAnimatedBrush(data.list[0])

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .shadow(elevation = 25.dp, CircleShape) // Apply shadow first
                .size(230.dp),
//            shape = CircleShape,
//            color = SkyBlue
            color = Color.Transparent,
            shadowElevation = 1.dp
        ) {
            Box(
                modifier = Modifier
//                .fillMaxWidth()
                    .fillMaxSize()
                    .background(animatedBrush),
                contentAlignment = Alignment.Center
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherStateImage(todayImageUrl = todayImageUrl)
                    Text(
                        text = today.temp.day.toString() + (if (isImperial) "°F" else "°C"),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = today.weather[0].main,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

        }
        HumidityWindPressureRow(weather = today, isImperial = isImperial)
        HorizontalDivider()
        SunsetSunRiseRow(data)
    }

}





