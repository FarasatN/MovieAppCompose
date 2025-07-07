package com.farasatnovruzov.movieappcompose.screens.home

import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.data.NotesDataSource
import com.farasatnovruzov.movieappcompose.model.Movie
import com.farasatnovruzov.movieappcompose.model.Note
import com.farasatnovruzov.movieappcompose.model.getMovies
import com.farasatnovruzov.movieappcompose.navigation.MovieScreens
import com.farasatnovruzov.movieappcompose.screens.note.NoteScreen
import com.farasatnovruzov.movieappcompose.screens.note.NoteViewModel
import com.farasatnovruzov.movieappcompose.widgets.MovieRow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
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
                        "Movies", maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                })
        }) { innerPadding ->
        MainContent(navController, innerPadding)
    }
}

@Composable
fun MainContent(
    navController: NavController,
    paddingValues: PaddingValues, movieList: List<Movie> = getMovies()
) {
    Column {
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(movieList) {
                MovieRow(movie = it) { movie ->
                    Log.d("TAG", "MainContent: $movie")
                    navController.navigate(route = MovieScreens.DetailScreen.name + "/$movie")
                }
            }
        }

        Surface {
            NotesApp()
        }
    }
}


@Composable
fun NotesApp(noteViewModel: NoteViewModel = viewModel()) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val notes = remember{
//            mutableStateListOf<Note>()
//        }
        val notesList = noteViewModel.getAllNotes()
        NoteScreen(
//                    notes = NotesDataSource().loadNotes(),
            notes = notesList,
            onAddNote = {
                noteViewModel.addNote(it)
            },
            onRemoveNote = {
                noteViewModel.removeNote(it)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(context = LocalContext.current))
}