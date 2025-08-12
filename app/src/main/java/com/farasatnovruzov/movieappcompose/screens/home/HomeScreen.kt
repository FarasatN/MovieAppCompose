package com.farasatnovruzov.movieappcompose.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.screens.questions.QuestionsViewModel
import com.farasatnovruzov.movieappcompose.screens.questions.TriviaHome


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(navController: NavController,
//               noteViewModel:NoteViewModel,
               questionsViewModel: QuestionsViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ), title = {
                    Text(
//                        "Movies",
//                        "Note",
                        "Trivia",
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                })
        }) { innerPadding ->
        MainContent(
//            navController,
            innerPadding,
//            getMovies(),
//            noteViewModel,
            questionsViewModel
        )
    }
}

@Composable
fun MainContent(
//    navController: NavController,
    paddingValues: PaddingValues,
//    movieList: List<Movie> = getMovies(),
//    noteViewModel: NoteViewModel,
    questionsViewModel: QuestionsViewModel
) {
    Column {
//        LazyColumn(
//            modifier = Modifier.padding(paddingValues)
//        ) {
//            items(movieList) {
//                MovieRow(movie = it) { movie ->
//                    Log.d("TAG", "MainContent: $movie")
//                    navController.navigate(route = MovieScreens.DetailScreen.name + "/$movie")
//                }
//            }
//        }


        Surface(color = MaterialTheme.colorScheme.background,modifier = Modifier.padding(paddingValues)) {
            //view model different options:
//            NotesApp(noteViewModel = noteViewModel)

            TriviaHome(
                questionsViewModel
            )


        }
    }
}



//@Composable
//fun NotesApp(noteViewModel: NoteViewModel) {
////        val notes = remember{
////            mutableStateListOf<Note>()
////        }
//
////        val notesList = noteViewModel.getAllNotes()
//    val notesList = noteViewModel.noteList.collectAsState().value
//    NoteScreen(
////                    notes = NotesDataSource().loadNotes(),
//        notes = notesList,
//        onAddNote = {
//            noteViewModel.addNote(it)
//        },
//        onRemoveNote = {
//            noteViewModel.removeNote(it)
//        }
//    )
//
//}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(navController = NavController(context = LocalContext.current))
//}