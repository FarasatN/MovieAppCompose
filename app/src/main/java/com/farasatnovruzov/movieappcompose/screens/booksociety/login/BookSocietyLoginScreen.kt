package com.farasatnovruzov.movieappcompose.screens.booksociety.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.movieappcompose.components.booksociety.EmailInput
import com.farasatnovruzov.movieappcompose.components.booksociety.PasswordInput
import com.farasatnovruzov.movieappcompose.components.booksociety.ReaderLogo
import com.farasatnovruzov.movieappcompose.navigation.booksociety.BookSocietyScreens
import com.farasatnovruzov.movieappcompose.ui.theme.CustomBlue


@Composable
fun BookSocietyLoginScreen(
    navController: NavController,
    viewModel: BookSocietyLoginScreenViewModel = viewModel()
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ReaderLogo()
            if (showLoginForm.value) {
                UserForm(loading = false, isCreateAccount = false) { email, password ->
                    viewModel.signInWithEmailAndPassword(email, password){
                        Log.d("login", "BookSocietyLoginScreen: $email and $password")
                        navController.navigate(BookSocietyScreens.HomeScreen.name)
                    }
                }
            } else {
                UserForm(loading = false, isCreateAccount = true) { email, password ->
                    viewModel.createUserWithEmailAndPassword(email, password){
                        Log.d("login", "BookSocietyLoginScreen: $email and $password")
                    }
                    navController.navigate(BookSocietyScreens.HomeScreen.name)
                }
            }

            Spacer(modifier = Modifier.padding(15.dp))
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text = if (showLoginForm.value) stringResource(R.string.sign_up) else stringResource(R.string.login)
                Text(text = stringResource(R.string.new_user))
                Text(
                    text = text,
                    color = CustomBlue,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        },
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    // Regex for email validation
    val emailRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+\$")
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && email.value.matches(emailRegex) && password.value.trim().isNotEmpty() && password.value.length >= 6
    }
    val modifier = Modifier
        .fillMaxHeight(.5f)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(
        modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isCreateAccount) Text(
            text = stringResource(R.string.create_account),
            modifier = Modifier.padding(4.dp),
            color = CustomBlue
        )
        else Text(text = "")
        EmailInput(
            modifier = Modifier
//                .focusRequester(passwordFocusRequest)
                .align(alignment = Alignment.Start)
            ,
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            })

        PasswordInput(
            modifier = Modifier
                .focusRequester(passwordFocusRequest)
                .align(alignment = Alignment.Start)
            ,
            passwordState = password,
            labelId = stringResource(R.string.password),
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
//                keyboardController?.hide()
            })

        SubmitButton(
            textId = if (isCreateAccount) stringResource(R.string.create_account)
                     else stringResource(R.string.login),
            loading = loading,
            validInputs = valid
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }

}


@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = CustomBlue
        )
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }

}
