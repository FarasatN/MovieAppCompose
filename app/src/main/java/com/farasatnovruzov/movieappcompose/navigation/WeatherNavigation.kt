package com.farasatnovruzov.movieappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farasatnovruzov.movieappcompose.screens.weather.AboutScreen
import com.farasatnovruzov.movieappcompose.screens.weather.FavoritesScreen
import com.farasatnovruzov.movieappcompose.screens.weather.MainScreen
import com.farasatnovruzov.movieappcompose.screens.weather.SearchScreen
import com.farasatnovruzov.movieappcompose.screens.weather.SettingsScreen
import com.farasatnovruzov.movieappcompose.screens.weather.WeatherSplashScreen

@Preview(showBackground = true)
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        composable(WeatherScreens.MainScreen.name) {
            MainScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.FavoritesScreen.name) {
            FavoritesScreen(navController = navController)
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }


    }
}
