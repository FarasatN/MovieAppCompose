package com.farasatnovruzov.movieappcompose.widgets

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.ImageRequest.*
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.farasatnovruzov.movieappcompose.model.getMovies


@Preview
@Composable
fun MovieRow(
    movie: com.farasatnovruzov.movieappcompose.model.Movie = getMovies()[0],
    onItemClick: (String) -> Unit = {}
) {
    val expanded = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clickable {
                onItemClick(movie.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = CircleShape,
                shadowElevation = 4.dp,
                tonalElevation = 4.dp,
            ) {
//                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Movie Icon")
                //Deprecated!
//                Image(
//                    painter = rememberAsyncImagePainter(movie.images.first()),
//                    contentDescription = "Movie Image",
//                    modifier = Modifier.clip(CircleShape),
//                    contentScale = ContentScale.Crop,
//                )

//                AsyncImage(
//                    model = movie.images[0],
//                    contentDescription = null,
//                )
                //--------------------
//                AsyncImge(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(movie.images[0])
//                        .crossfade(true) // Optional: add a crossfade animation
//                        .build(),
//                    contentDescription = "Image from internet",
//                    modifier = Modifier.fillMaxSize(),
//                    onLoading = {
//                        println("Image loading.... ")
//                    },
//                    onSuccess = { successState ->
//                        println("Image loaded successfully: ${successState.result.dataSource}")
//                    },
//                    onError = { errorState ->
//                        println("Image failed to load: ${errorState.result.throwable?.message}")
//                    }
//                )

                AsyncImage(
                    model = Builder(LocalContext.current)
                        .data(movie.images.first())
                        .crossfade(true)
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = "Movie Poster"
                )
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = movie.title, modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Director: ${movie.director}", modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Released: ${movie.year}", modifier = Modifier
                        .fillMaxWidth()
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Down Arrow",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            expanded.value = !expanded.value
                        },
                    tint = Color.DarkGray
                )
            }

        }

    }
}
