package com.farasatnovruzov.movieappcompose.screens.details

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest.Builder
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.farasatnovruzov.movieappcompose.model.Movie
import com.farasatnovruzov.movieappcompose.model.getMovies
import com.farasatnovruzov.movieappcompose.screens.home.MainContent
import com.farasatnovruzov.movieappcompose.widgets.MovieRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController,
                  movieId: String?
//                  movie: Movie?
) {
//    Surface(
//         modifier = Modifier.fillMaxHeight().fillMaxWidth()
//    ) {
//        Column(
//            horizontalAlignment = CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(style = MaterialTheme.typography.headlineMedium,text = movieData.toString(), textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline, modifier = Modifier.clickable(){
//                navController.popBackStack()
//            })
//        }

    val newMovieList = getMovies().filter { it.id == movieId }
//    val newMovieList = getMovies().filter { it.id == movie?.id }

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
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })

        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding) // This is the crucial line!
                .fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            MovieRow(movie = newMovieList.first())
//            Text(style = MaterialTheme.typography.headlineMedium,text = newMovieList[0].title, textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline, modifier = Modifier.clickable(){
//                navController.popBackStack()
//            })

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Text(text = "Movie Images")
            HorizontalScrollableImageView(newMovieList)
        }
    }
}

@Composable
private fun HorizontalScrollableImageView(newMovieList: List<Movie>) {
    LazyRow {
        items(newMovieList[0].images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ) {
                println(image)
                AsyncImage(
                    model = Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
//                                .transformaProjecttions()
                        .build(),
                    contentDescription = "Movie Poster",
//                            contentScale = ContentScale.FillWidth,
//                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}
