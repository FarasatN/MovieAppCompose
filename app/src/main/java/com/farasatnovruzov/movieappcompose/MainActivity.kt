package com.farasatnovruzov.movieappcompose

import FinanceSection
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.farasatnovruzov.movieappcompose.components.bankingui.CardSection
import com.farasatnovruzov.movieappcompose.components.bankingui.CurrenciesSection
import com.farasatnovruzov.movieappcompose.components.bankingui.WalletSection
import com.farasatnovruzov.movieappcompose.navigation.bankingui.BottomNavigationBar
import com.farasatnovruzov.movieappcompose.navigation.booksociety.BookSocietyNavigation
import com.farasatnovruzov.movieappcompose.ui.theme.BookSocietyAppComposeTheme
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            MyApp { ->
////            val noteViewModel = viewModel<NoteViewModel>()
////                val noteViewModel: NoteViewModel by viewModels()
//                val questionsViewModel: QuestionsViewModel by viewModels()
//                MovieNavigation(
////                    noteViewModel,
//                    questionsViewModel)
//
//            }

            //---------------------------------------------------
            //Weather App
//            WeatherApp()

            //BookSociety
//            BookSocietyApp()

//====================================================================================================
            //Banking UI
            BookSocietyAppComposeTheme {
//                HomeScreen()

//                RatingScreen()
                HalfRatingScreen()
            }
        }
    }
}


//**************************************************************************************
@Composable
fun HalfRatingBar(
    modifier: Modifier = Modifier,
    rating: Float = 0f, // Məsələn: 3.5f
    maxStars: Int = 5,
    starSize: Dp = 48.dp,
    starColor: Color = Color(0xFFFFC107),
    onRatingChanged: (Float) -> Unit
) {
    Row(modifier = modifier) {
        for (i in 1..maxStars) {
            // Hər ulduz üçün vizual vəziyyəti müəyyən edirik
            val starValue = i.toFloat()
            val isFull = rating >= starValue
            val isHalf = rating >= (starValue - 0.5f) && rating < starValue

            Box(
                modifier = Modifier
                    .size(starSize)
                    .padding(2.dp)
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            // Ulduzun enini tapırıq və toxunulan X koordinatına baxırıq
                            val width = size.width
                            val isLeftHalf = offset.x < (width / 2)

                            val newRating = if (isLeftHalf) {
                                starValue - 0.5f // Sol hissəyə kliklənərsə .5
                            } else {
                                starValue // Sağ hissəyə kliklənərsə tam xal
                            }
                            onRatingChanged(newRating)
                        }
                    }
            ) {
                when {
                    isFull -> {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Full Star",
                            tint = starColor,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                    isHalf -> {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.StarHalf,
                            contentDescription = "Half Star",
                            tint = starColor,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                    else -> {
                        Icon(
                            imageVector = Icons.Filled.StarOutline,
                            contentDescription = "Empty Star",
                            tint = Color.Gray,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun HalfRatingScreen() {
    // Reaktiv Float state
    var userRating by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Seçilmiş Qiymət: $userRating",
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )

        HalfRatingBar(
            rating = userRating,
            onRatingChanged = { newRating ->
                userRating = newRating
            }
        )
    }
}


//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//Rating Bar
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0, // Cari qiymətləndirmə xalı (məs: 3)
    maxStars: Int = 5, // Maksimum ulduz sayı
    starSize: Dp = 48.dp, // Ulduzun ölçüsü
    starColor: Color = Color(0xFFFFC107), // Ulduzun rəngi (Sarı/Qızılı)
    onRatingChanged: (Int) -> Unit // İstifadəçi ulduza kliklədikdə çağırılan lambda
) {
    Row(modifier = modifier) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating

            Icon(
                imageVector = if (isSelected) Icons.Filled.Star else Icons.Filled.StarOutline,
                contentDescription = "$i Star",
                tint = if (isSelected) starColor else Color.Gray,
                modifier = Modifier
                    .size(starSize)
                    .padding(2.dp)
                    .clickable {
                        onRatingChanged(i) // Kliklənən ulduzun indeksini ötürürük
                    }
            )
        }
    }
}
//@Preview
@Composable
fun RatingScreen() {
    // Cari qiymətləndirmə dəyərini saxlayacaq state
    var userRating by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Seçilmiş Qiymət: $userRating",
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )

        // Yaratdığımız RatingBar
        RatingBar(
            rating = userRating,
            onRatingChanged = { newRating ->
                userRating = newRating // State yenilənir
            }
        )
    }
}

//*******************************************************************************************















@Composable
fun HomeScreen(){
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            WalletSection()
            CardSection()
            Spacer(modifier = Modifier.height(16.dp))
            FinanceSection()
            CurrenciesSection()
        }
    }
}




//===========================================================




@Composable
fun BookSocietyApp() {
    BookSocietyAppComposeTheme {
        val db  = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"

        Surface(
            color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Log.d("TAGG", "BookSocietyApp: MainActivity")
//                db.collection("users")
//                    .add(user)
//                    .addOnSuccessListener {
//                        Log.d("FB", "DocumentSnapshot added with ID: ${it.id}")
//                    }
//                    .addOnFailureListener { e ->
//                        Log.w("FB", "Error adding document", e)
//                    }
                BookSocietyNavigation()
            }
        }
    }
}


//@Composable
//fun WeatherApp() {
//    WeatherAppComposeTheme {
//        Surface(
//            color = MaterialTheme.colorScheme.background,
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Column(
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Log.d("TAGG", "WeatherApp: MainActivity")
//                WeatherNavigation()
//            }
//        }
//    }
//}


//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun MyApp(content: @Composable () -> Unit) {
//    MovieAppComposeTheme {
//        content()
//    }
//}


//@Preview(showBackground = true)
//@Composable
//fun DefaultAppPreview() {
//    MyApp { ->
//        MovieNavigation(noteViewModel)
//    }
//}