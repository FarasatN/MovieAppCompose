package com.farasatnovruzov.movieappcompose.screens.booksociety.details

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.farasatnovruzov.movieappcompose.components.booksociety.BookSocietyAppBar
import com.farasatnovruzov.movieappcompose.components.booksociety.RoundedButton
import com.farasatnovruzov.movieappcompose.data.Resource
import com.farasatnovruzov.movieappcompose.model.booksociety.Item
import com.farasatnovruzov.movieappcompose.model.booksociety.MBook
import com.farasatnovruzov.movieappcompose.navigation.booksociety.BookSocietyScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun BookSocietyDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: BookSocietyDetailViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        BookSocietyAppBar(
            title = "Book Details",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
//            navController.popBackStack()
            navController.navigate(BookSocietyScreens.SearchScreen.name)
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
//                var bookInfo by remember { mutableStateOf<Resource<Item>>(Resource.Loading()) }
//                LaunchedEffect(bookId) {
//                    bookInfo = viewModel.getBookInfo(bookId)
//                }
                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                    value = viewModel.getBookInfo(bookId)
                }.value

                if (bookInfo.data?.volumeInfo?.title.isNullOrEmpty()) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        Text(text = "Loading...")
                    }
                } else {
//                    Text(text = "Book Id: ${bookInfo.data.volumeInfo.title}")
                    ShowBookDetails(bookInfo, navController)
                }

            }

        }
    }

}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id
    Card(
        modifier = Modifier.padding(34.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = CircleShape
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = bookData?.imageLinks?.thumbnail),
            contentDescription = "Book Image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp)
        )
    }
    bookData?.title?.let {
        Text(
            text = it,
            fontSize = 20.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            maxLines = 19
        )
    }
    Text(text = "Authors: ${bookData?.authors}")
    Text(text = "Page Count: ${bookData?.pageCount}")
    Text(text = "Categories: ${bookData?.categories}")
    Text(text = "Published Date: ${bookData?.publishedDate}")
    Spacer(
        modifier = Modifier
            .height(5.dp)
    )
    val cleanDescription = HtmlCompat.fromHtml(
        bookData?.description ?: "",
        HtmlCompat.FROM_HTML_MODE_LEGACY
    ).toString()

    val localDims = LocalContext.current.resources.displayMetrics

    Surface(
        modifier = Modifier
            .height(localDims.heightPixels.dp.times(0.09f))
            .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.DarkGray)

    ) {

        LazyColumn(
            modifier = Modifier.padding(3.dp)
        ) {
            item {
                Text(text = cleanDescription)
            }

        }
    }


    //Buttons
    Row(
//        modifier = Modifier.padding(top = 6.dp),
//        horizontalArrangement = Arrangement.SpaceAround
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 30.dp,
                end = 30.dp
            ), // Yanlardan boşluq düymələri daraldır
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedButton(
            label = "Save",
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            //Save this book to the database
            val book = MBook(
                title = bookData?.title,
                authors = bookData?.authors.toString(),
                description = bookData?.description,
                categories = bookData?.categories.toString(),
                notes = "",
                photoUrl = bookData?.imageLinks?.thumbnail,
                publishedDate = bookData?.publishedDate,
                pageCount = bookData?.pageCount.toString(),
                rating = 0.0,
                googleBookId = googleBookId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            )
            saveToFireBase(book, navController = navController)

            //navController.popBackStack()
            //            navController.navigate(BookSocietyScreens.SearchScreen.name)
        }
        Spacer(modifier = Modifier.width(10.dp))
        RoundedButton(
            label = "Cancel",
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            navController.popBackStack()
        }
    }


}


//@Composable
fun saveToFireBase(book: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
    if (book.id.isNullOrEmpty()) {
        book.id = db.collection("books").document().id
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            println("Successfully updated document with ID: $docId")
                            navController.popBackStack()
//                            navController.navigate(BookSocietyScreens.SearchScreen.name)
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w("Error", "saveToFireBase: Error updating document", e)
                        println("Error updating document: $e")
                    }
            }

    } else {
        dbCollection.document(book.id.toString())
    }

}