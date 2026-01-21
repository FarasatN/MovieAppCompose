package com.farasatnovruzov.movieappcompose.navigation.booksociety

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farasatnovruzov.movieappcompose.screens.booksociety.BookSocietySplashScreen
import com.farasatnovruzov.movieappcompose.screens.booksociety.details.BookSocietyDetailsScreen
import com.farasatnovruzov.movieappcompose.screens.booksociety.home.BookSocietyHomeScreen
import com.farasatnovruzov.movieappcompose.screens.booksociety.login.BookSocietyLoginScreen
import com.farasatnovruzov.movieappcompose.screens.booksociety.search.BookSocietySearchScreen
import com.farasatnovruzov.movieappcompose.screens.booksociety.search.BookSocietySearchViewModel
import com.farasatnovruzov.movieappcompose.screens.booksociety.stats.BookSocietyStatsScreen
import com.farasatnovruzov.movieappcompose.screens.booksociety.update.BookSocietyUpdateScreen


@Composable
fun BookSocietyNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BookSocietyScreens.SplashScreen.name
    ) {
        composable(BookSocietyScreens.SplashScreen.name) {
            BookSocietySplashScreen(navController = navController)
        }
        composable(BookSocietyScreens.LoginScreen.name) {
            BookSocietyLoginScreen(navController = navController)
        }
        composable(BookSocietyScreens.HomeScreen.name) {
            BookSocietyHomeScreen(navController = navController)
        }
        composable(BookSocietyScreens.SearchScreen.name) {
            val searchViewModel = hiltViewModel<BookSocietySearchViewModel>()
            BookSocietySearchScreen(navController = navController, viewModel = searchViewModel)
        }
        composable(BookSocietyScreens.StatsScreen.name) {
            BookSocietyStatsScreen(navController = navController)
        }
        composable(BookSocietyScreens.DetailsScreen.name) {
            BookSocietyDetailsScreen(navController = navController)
        }
        composable(BookSocietyScreens.UpdateScreen.name) {
            BookSocietyUpdateScreen(navController = navController)
        }


    }

}