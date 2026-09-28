package com.farasatnovruzov.spendingtracker.core.presentation.util

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.farasatnovruzov.spendingtracker.core.presentation.ui.theme.SpendingTrackerAppComposeTheme

@Composable
fun Background(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.inversePrimary.copy(0.3f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    )
}

@Preview(
    name = "Dark Mode Preview",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun BackgroundDarkModePreview() {
    // MÜTLƏQDİR: Theme wrapper burada çağırılmalıdır
    SpendingTrackerAppComposeTheme(darkTheme = true) {
        Background()
    }
}

@Preview(
    name = "Light Mode Preview",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun BackgroundLightModePreview() {
    SpendingTrackerAppComposeTheme(darkTheme = false) {
        Background()
    }
}