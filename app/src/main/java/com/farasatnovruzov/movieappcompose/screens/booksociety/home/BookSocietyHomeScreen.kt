package com.farasatnovruzov.movieappcompose.screens.booksociety.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.components.booksociety.BookListCard
import com.farasatnovruzov.movieappcompose.components.booksociety.BookSocietyAppBar
import com.farasatnovruzov.movieappcompose.components.booksociety.FABContent
import com.farasatnovruzov.movieappcompose.components.booksociety.TitleSection
import com.farasatnovruzov.movieappcompose.model.booksociety.MBook
import com.farasatnovruzov.movieappcompose.navigation.booksociety.BookSocietyScreens
import com.farasatnovruzov.movieappcompose.ui.theme.CustomBlue
import com.google.firebase.auth.FirebaseAuth


@Composable
fun BookSocietyHomeScreen(
    navController: NavController = NavController(context = LocalContext.current),
    viewModel: BookSocietyHomeScreenViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        BookSocietyAppBar(
            title = "Book Society", showProfile = true, navController = navController
        )
    }, floatingActionButton = {
        FABContent {
            navController.navigate(BookSocietyScreens.SearchScreen.name)
        }
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HomeContent(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun HomeContent(navController: NavController, viewModel: BookSocietyHomeScreenViewModel) {
//    val listOfBooks = listOf(
//        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
//        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
//        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
//        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
//        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
//        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
//    )
    var listOfBooks = viewModel.data.value.data?.toMutableStateList()
    val currentUser = FirebaseAuth.getInstance().currentUser
    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data?.toMutableStateList()?.filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }?.toMutableStateList()
        println("Current User Id: ${currentUser?.uid.toString()}")
        println("Current User lists: ${listOfBooks.toString()}")
    }


    val currentUserName =
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) FirebaseAuth.getInstance().currentUser?.email?.split(
            "@"
        )?.get(0)
        else "N/A"

    Column(
        modifier = Modifier.padding(start = 2.dp, top = 4.dp, end = 2.dp, bottom = 4.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.align(alignment = Alignment.Start),
        ) {
            TitleSection(label = "Your reading \n activity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.5f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile Icon",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(BookSocietyScreens.StatsScreen.name)
                        }
                        .size(45.dp),
                    tint = CustomBlue,
                )
                Text(
                    text = currentUserName!!, modifier = Modifier.padding(2.dp), style = TextStyle(
                        color = CustomBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    ), maxLines = 1, overflow = TextOverflow.Clip, textAlign = TextAlign.Left
                )
                HorizontalDivider()
            }

        }


        SocialArea(
            books = listOfBooks!!.toList()
//                listOf(
//                MBook(
//                    id = "dadfa",
//                    title = "Hello Again",
//                    authors = ""
//                ))
            , navController = navController
        )

        TitleSection(label = "Reading List")
        BookListArea(listOfBooks = listOfBooks.toList(), navController = navController)


    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks, navController = navController) {
        //on card clicked to go to details screen
        navController.navigate(BookSocietyScreens.UpdateScreen.name+"/$it")

    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    navController: NavController,
    onCardPressed: (String) -> Unit
) {
    val scrollState = androidx.compose.foundation.rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks) {
            BookListCard(book, navController) {
                onCardPressed(it)
            }
        }

    }
}

@Composable
fun SocialArea(books: List<MBook>, navController: NavController) {
    BookListCard(book = if (books.isNotEmpty()) books[0] else MBook(), navController = navController)
}
