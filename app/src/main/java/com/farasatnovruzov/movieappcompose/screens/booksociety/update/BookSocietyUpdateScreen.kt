package com.farasatnovruzov.movieappcompose.screens.booksociety.update

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.farasatnovruzov.movieappcompose.components.booksociety.BookSocietyAppBar
import com.farasatnovruzov.movieappcompose.components.booksociety.InputField
import com.farasatnovruzov.movieappcompose.components.booksociety.RatingBar
import com.farasatnovruzov.movieappcompose.components.booksociety.RoundedButton
import com.farasatnovruzov.movieappcompose.data.DataOrException
import com.farasatnovruzov.movieappcompose.model.booksociety.MBook
import com.farasatnovruzov.movieappcompose.screens.booksociety.home.BookSocietyHomeScreenViewModel
import com.farasatnovruzov.movieappcompose.utils.formatDate
import com.farasatnovruzov.movieappcompose.utils.formatDateTimestamp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun BookSocietyUpdateScreen(
    navController: NavController,
    bookItemId: String,
    viewModel: BookSocietyHomeScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            BookSocietyAppBar(
                title = "Book Update",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                showProfile = false,
                navController = navController,
//                elevation = 10.dp,
                onBackArrowClicked = {
                    navController.popBackStack()
                },
//                padding = 8.dp,
            )
        }
    ) {
        val bookInfo = produceState<DataOrException<List<MBook>, Boolean, Exception>>(
            initialValue = DataOrException(data = emptyList(), loading = true, e = null)
        ) {
            value = viewModel.data.value
        }.value

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(3.dp),
                verticalArrangement = Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Log.d(
                    "BookSocietyUpdateScreen",
                    "BookSocietyUpdateScreen: ${viewModel.data.value.data.toString()}"
                )
                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {
//                    Text(text = viewModel.data.value.data?.get(0)?.title.toString())
                    Surface(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                        shape = CircleShape,
                        shadowElevation = 4.dp
                    ) {
                        ShowBookUpdate(
                            bookInfo = viewModel.data.value,
                            bookItemId = bookItemId
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    ShowSimpleForm(book = bookInfo.data!!.first { mBook ->
                        mBook.googleBookId == bookItemId
                    }, navController = navController)

                }
            }
        }
    }
}

@Composable
fun ShowSimpleForm(
    book: MBook,
    navController: NavController
) {
    val notesText = remember {
        mutableStateOf("")
    }
    val isStartedReading = remember {
        mutableStateOf(false)
    }
    val isFinishedReading = remember {
        mutableStateOf(false)
    }
    val ratingVal = remember {
        mutableStateOf(0)
    }

    SimpleForm(
        defaultValue = book.notes.toString().ifEmpty { "No thoughts available." }
    ) { note ->
        notesText.value = note
    }

    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(
            onClick = {
                isStartedReading.value = true
            },
            enabled = book.startReading == null
        ) {
            if (book.startReading == null) {
                if (!isStartedReading.value) {
                    Text(text = "Started Reading")
                } else {
                    Text(
                        text = "Started Reading",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            } else {
                Text("Started on: ${formatDateTimestamp(book.startReading)}")//Todo: format data
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(
            onClick = { isFinishedReading.value == true },
            enabled = book.finishedReading == null
        ) {
            if (book.finishedReading == null) {
                if (!isFinishedReading.value) {
                    Text(text = "Mark as Read")
                } else {
                    Text(text = "Finished Reading!")
                }
            } else {
                Text(text = "Finished on: ${book.finishedReading}")//Todo: format data
            }
        }

    }

    Text(text = "Rating", modifier = Modifier.padding(4.dp))
    book.rating?.toInt().let {
        if (it != null) {
            RatingBar(rating = it) { rating ->
                ratingVal.value = rating
                Log.d("rating", "rating: ${ratingVal.value}")
            }
        }
    }

    Spacer(modifier = Modifier.padding(bottom = 15.dp))
    Row {
        val changedNotes = book.notes != notesText.value
        val changedRating = book.rating?.toInt() != ratingVal.value
        val isFinishedTimeStamp =
            if (isFinishedReading.value) Timestamp.now() else book.finishedReading
        val isStartedTimeStamp = if (isStartedReading.value) Timestamp.now() else book.startReading
        val bookUpdate =
            changedNotes || changedRating || isStartedReading.value || isFinishedReading.value
        val bookToUpdate = hashMapOf(
            "finished_reading_at" to isFinishedTimeStamp,
            "start_reading_at" to isStartedTimeStamp,
            "notes" to notesText.value,
            "rating" to ratingVal.value
        ).toMap()
        RoundedButton(label = "Update") {
            if (bookUpdate) {
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.id!!)
                    .update(bookToUpdate)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("BookUpdate", "BookUpdate: Successfully updated document")
                            navController.popBackStack()
                        }
                    }.addOnFailureListener {
                        Log.w("ErrorUpdate", "BookUpdate: Error updating document", it)
                    }
            }
            navController.popBackStack()

        }
        Spacer(modifier = Modifier.width(100.dp))
        RoundedButton(label = "Delete")

    }


}

@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Hello Again",
    onSearch: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val textFieldValuestate = rememberSaveable {
            mutableStateOf(defaultValue)
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(
            textFieldValuestate.value
        ) {
            textFieldValuestate.value.trim().isNotEmpty()
        }
        InputField(
            modifier = modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(3.dp)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            emailState = textFieldValuestate,
            labelId = "Book Title",
            enabled = !loading,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(textFieldValuestate.value.trim())
                textFieldValuestate.value = ""
                keyboardController?.hide()
            }
        )

    }
}

@Composable
fun ShowBookUpdate(
    bookInfo: DataOrException<List<MBook>, Boolean, Exception>,
    bookItemId: String
) {
    Row {
        Spacer(modifier = Modifier.width(43.dp))
        if (bookInfo.data != null) {
            Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center) {
                CardListItem(book = bookInfo.data!!.first { mBook ->
                    mBook.googleBookId == bookItemId
                }, onPressDetails = {})
            }
        }
    }
}

@Composable
fun CardListItem(book: MBook, onPressDetails: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 4.dp,
                bottom = 8.dp
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable {

            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {
        Row(horizontalArrangement = Arrangement.Start) {
            Image(
                painter = rememberAsyncImagePainter(model = book.photoUrl.toString()),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp,
                            topEnd = 20.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            )
            Column {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.authors.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp, top = 2.dp, bottom = 8.dp
                    )
                )
                Text(
                    text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp, top = 0.dp, bottom = 8.dp
                    )
                )
            }
        }
    }
}