package com.farasatnovruzov.movieappcompose.screens.questions

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.farasatnovruzov.movieappcompose.components.Questions

@Composable
fun TriviaHome(questionsViewModel: QuestionsViewModel = hiltViewModel()) {
    Questions(
        questionsViewModel
    )

}

