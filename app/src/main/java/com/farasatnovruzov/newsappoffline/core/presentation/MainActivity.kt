package com.farasatnovruzov.newsappoffline.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.farasatnovruzov.newsappoffline.core.presentation.ui.theme.NewsAppOfflineComposeTheme
import com.farasatnovruzov.spendingtracker.core.presentation.util.Screen

class MainActivity : ComponentActivity() {
//    https://newsdata.io/api/1/latest?apikey=pub_afa06c3d3e9f4d75a0fde98a6fe00010

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppOfflineComposeTheme {
                Navigation(modifier = Modifier.fillMaxSize())
            }
        }
    }


    @Composable
    fun Navigation(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screen.SpendingOverview,
        ) {

        }
    }
}