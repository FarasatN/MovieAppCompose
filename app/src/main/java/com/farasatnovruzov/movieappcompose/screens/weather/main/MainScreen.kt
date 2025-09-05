package com.farasatnovruzov.movieappcompose.screens.weather.main

import android.util.Log
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
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
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
import com.farasatnovruzov.movieappcompose.ui.theme.SkyBlue
import com.farasatnovruzov.movieappcompose.utils.Constants.DEFAULT_CITY
import com.farasatnovruzov.movieappcompose.utils.formatDate
import com.farasatnovruzov.movieappcompose.widgets.weather.GetWeatherAnimatedBrush
import com.farasatnovruzov.movieappcompose.widgets.weather.HumidityWindPressureRow
import com.farasatnovruzov.movieappcompose.widgets.weather.SunsetSunRiseRow
import com.farasatnovruzov.movieappcompose.widgets.weather.WeatherAppBar
import com.farasatnovruzov.movieappcompose.widgets.weather.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    val curCity: String = if (city!!.isBlank()) DEFAULT_CITY else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
//    val defaultUnit = if (unitFromDb.isNullOrEmpty()) "Metric (C)" else unitFromDb[0].unit
    val defaultUnit = unitFromDb.lastOrNull()?.unit ?: "Metric (C)"
    Log.d("defaultUnit", "defaultUnit: $defaultUnit")
//    var unit by rememberSaveable { mutableStateOf("metric")}
//    var isMetric by rememberSaveable {mutableStateOf(false)}
//    unit = defaultUnit.split(" ")[0].lowercase()
//    isMetric = unit == "metric"
    val unit = defaultUnit.split(" ")[0].lowercase()
    val isMetric = unit == "metric"
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true), key1 = curCity, key2 = unit
    ) {
        value = mainViewModel.getWeatherData(
            city = curCity, units = unit
        )
    }.value

    if (weatherData.loading == true) {
//        CircularProgressIndicator()
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = SkyBlue)
        }
    } else if (weatherData.data != null) {
        Log.d("isMetric: ", "isMetric: $isMetric")
        MainScaffold(
            weather = weatherData.data!!, navController, isMetric = isMetric
        )
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController, isMetric: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + ",${weather.city.country}",
//            icon = Icons.AutoMirrored.Filled.ArrowBack,
            elevation = 25.dp, padding = 0.dp, onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }, navController = navController
        )
    }) { paddingValues ->
        MainContent(data = weather, paddingValues, isMetric)
    }

}


@Composable
fun MainContent(
    data: Weather, paddingValues: PaddingValues = PaddingValues(), isMetric: Boolean
) {
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
            text = formatDate((today.dt).toLong()).split(",")[0] + ", " + formatDate((today.dt).toLong()).split(
                ","
            )[1].substring(0, 7),
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
                .size(230.dp), color = Color.Transparent, shadowElevation = 1.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
//                    .background(animatedBrush),
                    .drawWithCache {
                        onDrawBehind {
                            drawRect(animatedBrush)
                        }
                    }, contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherStateImage(todayImageUrl = todayImageUrl)
                    Text(
                        text = today.temp.day.toInt().toString() + (if (isMetric) "°C" else "°F"),
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
        HumidityWindPressureRow(weather = today, isMetric = isMetric)
        HorizontalDivider()
        SunsetSunRiseRow(data)
    }
}





