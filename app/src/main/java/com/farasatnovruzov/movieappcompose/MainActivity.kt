package com.farasatnovruzov.movieappcompose

import android.annotation.SuppressLint
import android.graphics.Movie
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farasatnovruzov.movieappcompose.ui.theme.MovieAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp { paddingValues ->
                MainContent(paddingValues = paddingValues)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(content: @Composable (paddingValues: androidx.compose.foundation.layout.PaddingValues) -> Unit) {
    MovieAppComposeTheme {
        Scaffold(
            topBar = {
                TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ), title = {
                    Text(
                        "Movies",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                })
            }) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Composable
fun MainContent(
    paddingValues: PaddingValues,
    movieList: List<String> = listOf(
        "Movie1",
        "Movie2",
        "Movie3",
        "Movie4",
        "Movie5",
        "Movie6",
        "Movie7",
        "Movie8",
        "Movie9",
        "Movie10",
        "Movie11",
        "Movie12",
        "Movie13",
        "Movie14",
        "Movie15",
        "Movie16",
        "Movie17",
        "Movie18"
    )
) {
    Column {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ){
            items(movieList) {
                MovieRow(it)
            }
        }

    }

}

@Composable
fun MovieRow(movie: String) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(130.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Surface(modifier = Modifier
                .padding(12.dp)
                .size(100.dp),
                shape = CircleShape,
                shadowElevation = 4.dp,
                tonalElevation = 4.dp,
                ) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Movie Icon",)
            }
            Text(
                text = movie,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultAppPreview() {
    MyApp {paddingValues ->
        MainContent(paddingValues)
    }
}