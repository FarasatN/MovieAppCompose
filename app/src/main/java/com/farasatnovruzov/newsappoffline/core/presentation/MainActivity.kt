package com.farasatnovruzov.newsappoffline.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.farasatnovruzov.newsappoffline.core.presentation.ui.theme.NewsAppOfflineComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppOfflineComposeTheme {
                AppNavigation()
            }
        }
    }
}
