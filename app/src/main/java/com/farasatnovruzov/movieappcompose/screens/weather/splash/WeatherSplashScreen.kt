package com.farasatnovruzov.movieappcompose.screens.weather.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.movieappcompose.navigation.weather.WeatherScreens
import kotlinx.coroutines.delay

//@Preview(showBackground = true)
@Composable
fun WeatherSplashScreen(
    navController: NavController,
) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800, easing = {
                OvershootInterpolator(8f).getInterpolation(it)
            })
        )
        delay(3000L)
        navController.navigate(WeatherScreens.MainScreen.name)
    })

    Surface(
        modifier = Modifier
            .padding(25.dp)
//            .fillMaxSize(0.4f)
            .size(350.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(width = 2.dp, color = Color.LightGray),
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                contentScale = ContentScale.Fit,
//                contentDescription = "Weather App",
//                modifier = Modifier.size(100.dp)
//            )
            LottieAnimationLoader(
//                modifier = Modifier.padding(20.dp)
            )
            Text(
                text = "Find the Sun?",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.LightGray
            )

        }
    }
}

@Composable
fun LottieAnimationLoader(
//    modifier: Modifier
) {
    val compositeResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId = R.raw.weather)
    )
    val progressAnimation = animateLottieCompositionAsState(
        composition = compositeResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    LottieAnimation(
        composition = compositeResult.value,
        progress = progressAnimation.progress
    )
}