package com.farasatnovruzov.movieappcompose.screens.walletui

import android.graphics.fonts.Font
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.movieappcompose.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = {
            Box(
                modifier = modifier.padding(end = 20.dp)
            ) {
                Box(modifier = Modifier.clip(CircleShape)
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.Center))
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior
    ){
        Text(text = "Wallet",
            fontSize = 40.sp,
            fontFamily = Font(R.fon)
        )

    }

}