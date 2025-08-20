package com.farasatnovruzov.movieappcompose.screens.weather.main

import android.R.attr.data
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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
import kotlinx.coroutines.delay

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState(
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
        SunsetSunRiseRow(data)
    }

}

@Composable
fun SunsetSunRiseRow(data: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.WbSunny,
                contentDescription = "Sunrise Icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDate(data.list[0].sunrise.toLong()),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(2.dp)
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.WbTwilight,
                contentDescription = "Sunset Icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDate(data.list[0].sunset.toLong()),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(2.dp)
            )
        }

    }

    Text(
        "This Week",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(),
        color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 5.dp
    ) {

        LazyColumn(
            modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
//        PeriodicWeatherSurface {
//            // Your content here, e.g., a centered text
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Current Weather: ${weather.weather[0].description.uppercase()}", // Custom function to display weather
//                    style = MaterialTheme.typography.headlineMedium,
//                    color = Color.White
//                )
//            }
//        }

            items(items = data.list) { item: WeatherItem ->
//                Text(text = fahrenheitToCelsius(item.temp.max))
                WeatherDetailRow(weather = data, item = item)

            }

        }
    }
}

@Composable
fun WeatherDetailRow(weather: Weather,item: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.list[0].weather[0].icon}.png"
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
//        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = formatDate(item.dt.toLong()).split(",")[0],
                modifier = Modifier.padding(start = 5.dp)
            )
            WeatherStateImage(imageUrl = imageUrl, size = 70.dp)

        }
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
    imageUrl: String,size: Dp = 120.dp
) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "icon image",
//        colorFilter = ColorFilter.tint(Color.Transparent),
        modifier = Modifier
            .size(size)
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


//-------------------------------------------------------------------
enum class WeatherType {
    Sunny, Night, Rainy
}

@Composable
fun PeriodicWeatherSurface(content: @Composable () -> Unit) {
    var currentWeather by remember { mutableStateOf(WeatherType.Sunny) }

    // Change weather every 10 seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(10_000L) // Use Long for delay
            currentWeather = when (currentWeather) {
                WeatherType.Sunny -> WeatherType.Night
                WeatherType.Night -> WeatherType.Rainy
                WeatherType.Rainy -> WeatherType.Sunny
            }
        }
    }

    when (currentWeather) {
        WeatherType.Sunny -> SunnySurface(content)
        WeatherType.Night -> NightSkySurface(content)
        WeatherType.Rainy -> RainySurface(content)
    }
}

@Composable
fun SunnySurface(content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "sunny")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "sunnyOffset"
    )
    val brush = Brush.linearGradient(
        colors = listOf(Color(0xFFFFE082), Color(0xFF64B5F6), Color(0xFF90CAF9)),
        start = Offset(0f, offset),
        end = Offset(offset, 0f)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ) {
        content()
    }
}

@Composable
fun NightSkySurface(content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "night")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "twinkle"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0xFF0D47A1), Color.Black),
                    radius = 1200f
                )
            )
    ) {
        Box(
            Modifier
                .size(8.dp)
                .align(Alignment.TopCenter)
                .alpha(alpha)
                .background(Color.White, shape = CircleShape)
        )
        content()
    }
}

@Composable
fun RainySurface(content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "rain")
    val ripple by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "ripple"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1565C0))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFF90CAF9).copy(alpha = 0.4f),
                radius = ripple,
                center = Offset(size.width / 2, size.height / 2),
                style = Stroke(width = 2f)
            )
        }
        content()
    }
}


//------------------------------------------------------------------



