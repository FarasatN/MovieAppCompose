package com.farasatnovruzov.movieappcompose.components.booksociety

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.movieappcompose.model.booksociety.MBook
import com.farasatnovruzov.movieappcompose.navigation.booksociety.BookSocietyScreens
import com.farasatnovruzov.movieappcompose.ui.theme.CustomBlue
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReaderLogo(modifier: Modifier = Modifier, color: Color = CustomBlue) {
    Text(
        text = stringResource(R.string.app_logo),
        modifier = modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = color
    )
}


@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = stringResource(id = R.string.email),
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    color: Color = CustomBlue,
) {

    InputField(
        modifier = modifier,
        emailState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction,
        color = color,
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    color: Color = CustomBlue,
//    hint: String = "Email",
) {
    var validationError by rememberSaveable { mutableStateOf<String?>(null) }
    // Regex for email validation
    val emailRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+\$")
    validationError = when {
//        emailState.value.isEmpty() -> "Please enter your email address."
        !emailState.value.matches(emailRegex) -> stringResource(R.string.invalid_email)
//        !emailState.value.contains("@") -> "Invalid email format"
//        !emailState.value.matches(emailRegex) -> "The email address you entered is not valid."
        else -> null
    }

    Column(
        modifier = modifier.padding(start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            value = emailState.value,
            onValueChange = {
                emailState.value = it
            },
            label = { Text(text = labelId) },
            singleLine = isSingleLine,
            textStyle = MaterialTheme.typography.bodyMedium,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = onAction,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = color,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = color,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = color,
            )
        )
        // Display the error message if it exists
        if (!validationError.isNullOrBlank() && !emailState.value.isEmpty()) {
            Text(
                text = validationError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

    }


}


@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    color: Color = CustomBlue
) {
    var validationError by rememberSaveable { mutableStateOf<String?>(null) }
    // Regex for email validation
    validationError = when {
//        passwordState.value.isEmpty() -> stringResource(R.string.invalid_password)
        passwordState.value.length < 6 -> stringResource(R.string.invalid_password)
        else -> null
    }

    val visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
    Column(
        modifier = modifier.padding(start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = passwordState.value,
            onValueChange = {
                passwordState.value = it
            },
            label = { Text(text = labelId) },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = imeAction
            ),
            visualTransformation = visualTransformation,
            trailingIcon = {
                val image = if (passwordVisibility.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // 4. Create an IconButton to toggle the state
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(imageVector = image, contentDescription = "Toggle password visibility")
                }
            },
            keyboardActions = onAction,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = color,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = color,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = color,
                focusedTrailingIconColor = color,
                unfocusedTrailingIconColor = Color.LightGray
            )
        )
        // Display the error message if it exists
        if (!validationError.isNullOrBlank() && !passwordState.value.isEmpty()) {
            Text(
                text = validationError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSocietyAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    elevation: Dp = 10.dp,
    padding: Dp = 8.dp,
    onBackArrowClicked: () -> Unit = {}
) {
    val roundedShape = RoundedCornerShape(
        topStartPercent = 10,
        topEndPercent = 10,
        bottomEndPercent = 10,
        bottomStartPercent = 10
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = padding, end = padding)
//            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .shadow(elevation = elevation, shape = roundedShape) // Apply shadow first
            .border(
                width = 0.dp,
//                color = MaterialTheme.colorScheme.secondary,
                color = Color.Transparent,
                shape = roundedShape
            ) // Apply border
            .clip(roundedShape), // Clip the content
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (showProfile) {
                        Image(
                            painter = painterResource(id = R.drawable.book_society_logo),
                            contentDescription = "Book Society Logo",
                            modifier = Modifier
//                                .clip(RoundedCornerShape(12.dp))
                                .scale(0.8f)
                        )
                    }
                    if (icon!=null){
                        Icon(imageVector = icon, contentDescription = "arrow back", tint = CustomBlue, modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        })
                    }
                    Spacer(modifier = Modifier.padding(40.dp))
                    Text(
                        text = title, color = CustomBlue,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                }
            },
            actions = {
                if (showProfile){
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut().run {
                            navController.navigate(BookSocietyScreens.LoginScreen.name)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = CustomBlue
                        )
                    }
                }

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            modifier = Modifier.padding(padding)
        )

    }

}


@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onTap()
        },
        shape = RoundedCornerShape(50.dp),
        containerColor = CustomBlue,
//        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = Color.White
        )

    }
}



@Composable
fun BookRating(score: Double = 4.5) {
    Surface(
        modifier = Modifier
            .height(70.dp)
//            .width(70.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        shadowElevation = 6.dp,
        color = Color.White,
        tonalElevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Filled.StarBorder,
                contentDescription = "Star icon",
                modifier = Modifier.padding(3.dp)
            )
            Text(
                text = score.toString(), style = MaterialTheme.typography.labelMedium
            )
        }
    }
}



@Composable
fun TitleSection(
    modifier: Modifier = Modifier, label: String
) {
    Surface(
        modifier = modifier.padding(start = 5.dp, top = 1.dp)
    ) {
        Column {
            Text(
                text = label, style = TextStyle(
                    color = CustomBlue, fontWeight = FontWeight.Bold, fontSize = 15.sp
                ), textAlign = TextAlign.Left
            )
        }
    }
}



//@Preview(showBackground = true)
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
    val spacing = 10.dp
    Card(
        modifier = Modifier
            .padding(16.dp)
            .height(210.dp)
            .width(230.dp)
            .clickable {
                onPressDetails(book.id.toString())
            }, shape = RoundedCornerShape(22), elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ), colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "http://books.google.com/books/content?id=UeR4DO_HvgoC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                    ),
                    contentDescription = "Book Society Logo",
                    modifier = Modifier
                        .height(140.dp)
                        .width(150.dp)
                        .padding(4.dp)
                )
//         Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav Icon",
                        modifier = Modifier.padding(bottom = 1.dp),
                        tint = CustomBlue,
                    )

                    BookRating(score = 4.5)
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 14.dp) // Sağdan biraz daha az boşluk bırakıldı
                    .fillMaxHeight() // Dikeyde kalan tüm alanı doldur
                    .fillMaxWidth(), // Yatayda tüm alanı doldur
                verticalAlignment = Alignment.Top, // İçerik başlangıçtan başlasın
                horizontalArrangement = Arrangement.SpaceBetween // İçerikleri yatayda ayır
            ) {
                // Sol Sütun: Başlık ve Yazar
                Column(
                    modifier = Modifier.weight(.5f)
                ) {
                    Text(
                        text = book.title.toString(),
                        modifier = Modifier.padding(4.dp),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = book.authors.toString(),
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                // Sağ Sütun: RoundedButton (Hizalama yapıldı)
                Column(
                    modifier = Modifier
                        .weight(.5f)
                        .fillMaxSize(), // Alanın %30'unu kullan ve kalan dikey alanı doldur
                    verticalArrangement = Arrangement.Bottom, // Dikeyde en alta hizala
                    horizontalAlignment = Alignment.End // Yatayda en sağa hizala
                ) {

                    RoundedButton(
                        label = "Read", radius = 55
                    )
                }
            }
        }
    }

}


//@Preview(showBackground = true)
@Composable
fun RoundedButton(
    label: String = "Reading", radius: Int = 50, color: Color = CustomBlue, onPress: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
//            .padding(20.dp)
            .clip(
                RoundedCornerShape(
                    topStartPercent = radius,
                    bottomEndPercent = radius,
                )
            ),
        color = color,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(50.dp)
                .clickable {
                    onPress()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = TextStyle(color = Color.White, fontSize = 12.sp),
                fontWeight = FontWeight.Bold,
//                color = Color.White,
            )
        }
    }

}

