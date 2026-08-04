package com.farasatnovruzov.movieappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.farasatnovruzov.movieappcompose.screens.walletui.TopBar
import com.farasatnovruzov.movieappcompose.ui.theme.BookSocietyAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
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
//                HalfRatingScreen()

                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
                    state = rememberTopAppBarState()
                )
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        TopBar(
                            scrollBehavior = scrollBehavior,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    bottomBar = {
                    },
                    floatingActionButton = {

                    }

                ){paddingValues ->
                    MainScreen(modifier = Modifier.fillMaxSize().padding(paddingValues))
                }
                
            }
        }
    }

    @Composable
    fun MainScreen(modifier: Modifier = Modifier){

    }
}


//**************************************************************************************
//@Composable
//fun HalfRatingBar(
//    modifier: Modifier = Modifier,
//    rating: Float = 0f, // Məsələn: 3.5f
//    maxStars: Int = 5,
//    starSize: Dp = 48.dp,
//    starColor: Color = Color(0xFFFFC107),
//    onRatingChanged: (Float) -> Unit
//) {
//    Row(modifier = modifier) {
//        for (i in 1..maxStars) {
//            // Hər ulduz üçün vizual vəziyyəti müəyyən edirik
//            val starValue = i.toFloat()
//            val isFull = rating >= starValue
//            val isHalf = rating >= (starValue - 0.5f) && rating < starValue
//
//            Box(
//                modifier = Modifier
//                    .size(starSize)
//                    .padding(2.dp)
//                    .pointerInput(Unit) {
//                        detectTapGestures { offset ->
//                            // Ulduzun enini tapırıq və toxunulan X koordinatına baxırıq
//                            val width = size.width
//                            val isLeftHalf = offset.x < (width / 2)
//
//                            val newRating = if (isLeftHalf) {
//                                starValue - 0.5f // Sol hissəyə kliklənərsə .5
//                            } else {
//                                starValue // Sağ hissəyə kliklənərsə tam xal
//                            }
//                            onRatingChanged(newRating)
//                        }
//                    }
//            ) {
//                when {
//                    isFull -> {
//                        Icon(
//                            imageVector = Icons.Filled.Star,
//                            contentDescription = "Full Star",
//                            tint = starColor,
//                            modifier = Modifier.matchParentSize()
//                        )
//                    }
//                    isHalf -> {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.StarHalf,
//                            contentDescription = "Half Star",
//                            tint = starColor,
//                            modifier = Modifier.matchParentSize()
//                        )
//                    }
//                    else -> {
//                        Icon(
//                            imageVector = Icons.Filled.StarOutline,
//                            contentDescription = "Empty Star",
//                            tint = Color.Gray,
//                            modifier = Modifier.matchParentSize()
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//@Preview
//@Composable
//fun HalfRatingScreen() {
//    // Reaktiv Float state
//    var userRating by remember { mutableFloatStateOf(0f) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Seçilmiş Qiymət: $userRating",
//            modifier = Modifier.padding(bottom = 16.dp),
//            fontSize = MaterialTheme.typography.titleLarge.fontSize
//        )
//
//        HalfRatingBar(
//            rating = userRating,
//            onRatingChanged = { newRating ->
//                userRating = newRating
//            }
//        )
//    }
//}


//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
////Rating Bar
//@Composable
//fun RatingBar(
//    modifier: Modifier = Modifier,
//    rating: Int = 0, // Cari qiymətləndirmə xalı (məs: 3)
//    maxStars: Int = 5, // Maksimum ulduz sayı
//    starSize: Dp = 48.dp, // Ulduzun ölçüsü
//    starColor: Color = Color(0xFFFFC107), // Ulduzun rəngi (Sarı/Qızılı)
//    onRatingChanged: (Int) -> Unit // İstifadəçi ulduza kliklədikdə çağırılan lambda
//) {
//    Row(modifier = modifier) {
//        for (i in 1..maxStars) {
//            val isSelected = i <= rating
//
//            Icon(
//                imageVector = if (isSelected) Icons.Filled.Star else Icons.Filled.StarOutline,
//                contentDescription = "$i Star",
//                tint = if (isSelected) starColor else Color.Gray,
//                modifier = Modifier
//                    .size(starSize)
//                    .padding(2.dp)
//                    .clickable {
//                        onRatingChanged(i) // Kliklənən ulduzun indeksini ötürürük
//                    }
//            )
//        }
//    }
//}
////@Preview
//@Composable
//fun RatingScreen() {
//    // Cari qiymətləndirmə dəyərini saxlayacaq state
//    var userRating by remember { mutableIntStateOf(0) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Seçilmiş Qiymət: $userRating",
//            modifier = Modifier.padding(bottom = 16.dp),
//            fontSize = MaterialTheme.typography.titleLarge.fontSize
//        )
//
//        // Yaratdığımız RatingBar
//        RatingBar(
//            rating = userRating,
//            onRatingChanged = { newRating ->
//                userRating = newRating // State yenilənir
//            }
//        )
//    }
//}

//*******************************************************************************************


//@Composable
//fun HomeScreen(){
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar()
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            WalletSection()
//            CardSection()
//            Spacer(modifier = Modifier.height(16.dp))
////            FinanceSection()
//            CurrenciesSection()
//        }
//    }
//}




//===========================================================



//
//@Composable
//fun BookSocietyApp() {
//    BookSocietyAppComposeTheme {
//        val db  = FirebaseFirestore.getInstance()
//        val user: MutableMap<String, Any> = HashMap()
//        user["first"] = "Ada"
//        user["last"] = "Lovelace"
//
//        Surface(
//            color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
//        ) {
//            Column(
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
////                Log.d("TAGG", "BookSocietyApp: MainActivity")
////                db.collection("users")
////                    .add(user)
////                    .addOnSuccessListener {
////                        Log.d("FB", "DocumentSnapshot added with ID: ${it.id}")
////                    }
////                    .addOnFailureListener { e ->
////                        Log.w("FB", "Error adding document", e)
////                    }
//                BookSocietyNavigation()
//            }
//        }
//    }
//}


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