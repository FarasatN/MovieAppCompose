package com.farasatnovruzov.movieappcompose.navigation.booksociety

enum class BookSocietyScreens {

    SplashScreen,
    LoginScreen,
    HomeScreen,
    SearchScreen,
    StatsScreen,
    DetailsScreen,
    UpdateScreen;

    companion object {
        fun fromRoute(route: String?): BookSocietyScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            SearchScreen.name -> SearchScreen
            StatsScreen.name -> StatsScreen
            DetailsScreen.name -> DetailsScreen
            UpdateScreen.name -> UpdateScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized.")
        }
    }

}