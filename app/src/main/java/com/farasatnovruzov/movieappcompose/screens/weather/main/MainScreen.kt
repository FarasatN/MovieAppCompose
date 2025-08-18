package com.farasatnovruzov.movieappcompose.screens.weather.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.farasatnovruzov.movieappcompose.data.DataOrException
import com.farasatnovruzov.movieappcompose.model.weather.Weather
import com.farasatnovruzov.movieappcompose.model.weather.WeatherItem
import com.farasatnovruzov.movieappcompose.utils.fahrenheitToCelsius
import com.farasatnovruzov.movieappcompose.utils.formatDate
import com.farasatnovruzov.movieappcompose.widgets.weather.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData("Baku")
//        value = mainViewModel.data.value
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
//        Text(text ="Main Screen: "+ weatherData.data!!.city.country)
        MainScaffold(weather = weatherData.data!!, navController)

    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + ",${weather.city.country}",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            elevation = 25.dp,
            padding = 0.dp,
        ) {
            navController.popBackStack()
        }
    }) { paddingValues ->
        MainContent(data = weather, paddingValues)
    }

}

@Composable
fun MainContent(data: Weather, paddingValues: PaddingValues = PaddingValues()) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatDate((data.list[0].dt).toLong()),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .shadow(elevation = 25.dp, CircleShape) // Apply shadow first
                .size(230.dp),
//            shape = CircleShape,
            color = Color(0xffb6f7ff)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = fahrenheitToCelsius(data.list[0].temp.day) + "°",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = data.list[0].weather[0].main,
                    style = MaterialTheme.typography.headlineMedium
                )

            }
        }
        HumidityWindPressureRow(weather = data.list[0])
        HorizontalDivider()
    }

}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.WaterDrop,
                contentDescription = "Humidity Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.Compress,
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.pressure} psi",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.WindPower,
                contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.speed} mph",
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}


@Composable
fun WeatherStateImage(
    imageUrl: String,
) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "icon image",
//        colorFilter = ColorFilter.tint(Color.Transparent),
        modifier = Modifier
            .size(100.dp)
//            .shadow(elevation = 5.dp, CircleShape) // Apply shadow first
//            .border(
//                width = 1.dp,
//                    color = Color(0xffb6f7ff),
////                color = Color.Transparent,
//                shape = CircleShape
//            ) // Apply border
//            .clip(CircleShape),
    )

}

