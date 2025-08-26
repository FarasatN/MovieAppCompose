package com.farasatnovruzov.movieappcompose.navigation.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farasatnovruzov.movieappcompose.screens.weather.about.AboutScreen
import com.farasatnovruzov.movieappcompose.screens.weather.favorites.FavoritesScreen
import com.farasatnovruzov.movieappcompose.screens.weather.main.MainScreen
import com.farasatnovruzov.movieappcompose.screens.weather.search.SearchScreen
import com.farasatnovruzov.movieappcompose.screens.weather.settings.SettingsScreen
import com.farasatnovruzov.movieappcompose.screens.weather.main.MainViewModel
import com.farasatnovruzov.movieappcompose.screens.weather.splash.WeatherSplashScreen

@Preview(showBackground = true)
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable(route = "$route/{city}", arguments = listOf(
            navArgument(name = "city") {
                type = NavType.StringType
            }
        )) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city)
            }
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
