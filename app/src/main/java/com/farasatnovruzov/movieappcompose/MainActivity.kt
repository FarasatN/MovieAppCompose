package com.farasatnovruzov.movieappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.farasatnovruzov.movieappcompose.navigation.MovieNavigation
import com.farasatnovruzov.movieappcompose.screens.note.NoteViewModel
import com.farasatnovruzov.movieappcompose.screens.questions.QuestionsViewModel
import com.farasatnovruzov.movieappcompose.ui.theme.MovieAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp { ->
//            val noteViewModel = viewModel<NoteViewModel>()
                val noteViewModel: NoteViewModel by viewModels()
                val questionsViewModel: QuestionsViewModel by viewModels()

                MovieNavigation(noteViewModel, questionsViewModel)

            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(content: @Composable () -> Unit) {
    MovieAppComposeTheme {
        content()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultAppPreview() {
//    MyApp { ->
//        MovieNavigation(noteViewModel)
//    }
//}