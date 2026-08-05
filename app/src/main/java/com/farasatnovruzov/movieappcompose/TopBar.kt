package com.farasatnovruzov.movieappcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = rememberTopAppBarState()),
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Box(
                modifier = modifier.padding(end = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.CenterStart)
                )
                Text(
                    text = "Wallet",
                    fontSize = 40.sp,
                    fontFamily = Font(R.font.play_bold).toFontFamily(),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.CenterStart)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray)
                        .align(Alignment.CenterEnd)

                )
            }
        },
    )


}