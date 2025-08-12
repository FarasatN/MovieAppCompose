package com.farasatnovruzov.movieappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.farasatnovruzov.movieappcompose.navigation.weather.WeatherNavigation
import com.farasatnovruzov.movieappcompose.ui.theme.WeatherAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            MyApp { ->
////            val noteViewModel = viewModel<NoteViewModel>()
////                val noteViewModel: NoteViewModel by viewModels()
//                val questionsViewModel: QuestionsViewModel by viewModels()
//                MovieNavigation(
////                    noteViewModel,
//                    questionsViewModel)
//
//            }

            //---------------------------------------------------
            //Weather App
            WeatherApp()
        }
    }
}

@Composable
fun WeatherApp(){
    WeatherAppComposeTheme {
        Surface(color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherNavigation()
            }

        }
    }


}

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun MyApp(content: @Composable () -> Unit) {
//    MovieAppComposeTheme {
//        content()
//    }
//}




//@Preview(showBackground = true)
//@Composable
//fun DefaultAppPreview() {
//    MyApp { ->
//        MovieNavigation(noteViewModel)
//    }
//}