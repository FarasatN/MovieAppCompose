package com.farasatnovruzov.movieappcompose.screens.booksociety.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.movieappcompose.components.booksociety.BookSocietyAppBar
import com.farasatnovruzov.movieappcompose.components.booksociety.FABContent
import com.farasatnovruzov.movieappcompose.model.booksociety.MBook
import com.farasatnovruzov.movieappcompose.navigation.booksociety.BookSocietyScreens
import com.farasatnovruzov.movieappcompose.ui.theme.CustomBlue
import com.google.firebase.auth.FirebaseAuth


@Composable
fun BookSocietyHomeScreen(
    navController: NavController = NavController(context = LocalContext.current)
) {

    Scaffold(
        topBar = {
            BookSocietyAppBar(
                title = "Book Society",
                showProfile = true,
                navController = navController
            )
        },
        floatingActionButton = {
            FABContent {
                navController.navigate(BookSocietyScreens.SearchScreen.name)
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HomeContent(navController = navController)
        }

    }

}

@Composable
fun HomeContent(navController: NavController) {
    val currentUserName = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    else "N/A"

    Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n activity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
//            Box{}
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
                    text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = TextStyle(
                        color = CustomBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    textAlign = TextAlign.Left
                )
                HorizontalDivider()

            }
        }
    }
}

@Composable
fun TitleSection(
    modifier: Modifier = Modifier,
    label: String
) {
    Surface(
        modifier = modifier
            .padding(start = 5.dp, top = 1.dp)
    ) {
        Column {
            Text(
                text = label,
                style = TextStyle(
                    color = CustomBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ),
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
fun SocialArea(books: List<MBook>, navController: NavController) {

}


@Composable
fun BookListCard(
    book: MBook = MBook(
        id = "1",
        title = "Book Title",
        authors = "Author Name",
        notes = "Book Notes",
    ),
    navController: NavController,
    onPressDetails: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing  = 10.dp
    Card(
        modifier = Modifier.padding(16.dp)
            .height(200.dp)
            .width(180.dp)
            .clickable {
                onPressDetails(book.id.toString())
            },
        shape = RoundedCornerShape(40),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column (
            modifier = Modifier.width(screenWidth.dp-(spacing*2)),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(painter = rememberAsyncImagePainter(
                    model = "book image"
                ),contentDescription = "Book Society Logo",
                    modifier = Modifier.height(140.dp).width(100.dp).padding(4.dp))
                Spacer(modifier = Modifier.width(50.dp))
            }

        }
    }

}