package com.farasatnovruzov.movieappcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.movieappcompose.design_system.AuthOption
import com.farasatnovruzov.movieappcompose.design_system.MyTextField

@Preview(showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth(0.25f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Login",
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        MyTextField(
            textFieldState = TextFieldState(),
            hint = "Email",
            leadingIcon = Icons.Outlined.Email,
            trailingIcon = Icons.Outlined.Check,
            modifier = Modifier.fillMaxWidth()
        )
        MyTextField(
            textFieldState = TextFieldState(),
            hint = "Password",
            leadingIcon = Icons.Filled.Lock,
            trailingText = "Forgot?",
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 17.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Text(
            text = "Or, login with...",
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(0.5f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AuthOption(
                image = R.drawable.ic_launcher_foreground, // Replace with your drawable resource
                tint = { Color.Blue }, // Functional lambda syntax for ColorProducer
            )
            AuthOption(
                image = R.drawable.ic_launcher_foreground, // Replace with your drawable resource
                tint = { Color.Blue }, // Functional lambda syntax for ColorProducer
            )
            AuthOption(
                image = R.drawable.ic_launcher_foreground, // Replace with your drawable resource
                tint = { Color.Blue }, // Functional lambda syntax for ColorProducer
            )
        }

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Text(
                text = "Don't have an account?",
                fontSize = 16.sp,
                modifier = Modifier.alpha(0.7f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Register",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable{}
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}