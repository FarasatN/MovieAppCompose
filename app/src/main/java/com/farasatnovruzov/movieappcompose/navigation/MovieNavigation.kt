package com.farasatnovruzov.movieappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farasatnovruzov.movieappcompose.screens.details.DetailsScreen
import com.farasatnovruzov.movieappcompose.screens.home.HomeScreen
import com.farasatnovruzov.movieappcompose.screens.note.NoteViewModel
import com.farasatnovruzov.movieappcompose.screens.questions.QuestionsViewModel

@Composable
fun MovieNavigation(noteViewModel: NoteViewModel, questionsViewModel: QuestionsViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = MovieScreens.HomeScreen.name){
        composable(MovieScreens.HomeScreen.name){
            //here we pass where this should lead us to
            HomeScreen(navController = navController, noteViewModel = noteViewModel, questionsViewModel = questionsViewModel)
        }
        composable(MovieScreens.DetailScreen.name+"/{movie}",
            arguments = listOf(navArgument(name = "movie"){type = NavType.StringType})){ backStackEntry ->
            DetailsScreen(navController = navController,backStackEntry.arguments?.getString("movie"))
        }
    }
}