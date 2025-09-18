package com.farasatnovruzov.movieappcompose.components.booksociety

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HideSource
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.movieappcompose.ui.theme.CustomBlue

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
        passwordState.value.length<6 -> stringResource(R.string.invalid_password)
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
                    Icon(imageVector  = image, contentDescription = "Toggle password visibility")
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


