package com.farasatnovruzov.spendingtracker.balance.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.spendingtracker.core.presentation.util.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun BalanceScreenCore(
    viewModel: BalanceViewModel = koinViewModel(),
    onSaveClick: () -> Unit,
) {
    BalanceScreenCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onSaveClick = {
            viewModel.onAction(BalanceAction.OnBalanceSaved)
            onSaveClick()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BalanceScreenCoreScreen(
    state: BalanceState,
    onAction: (BalanceAction) -> Unit,
    onSaveClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(start = 12.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "Update Balance",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 24.sp
                    )
                }
            )
        }
    ) { padding ->
        Background(modifier = Modifier.padding(padding))
        Column(modifier = Modifier
            .padding(padding)
            .padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "$${state.balance}",
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(38.dp))
            OutlinedTextField(
                value = state.balance.toString(),
                onValueChange = {
                    onAction(BalanceAction.OnBalanceChanged(it.toDouble() ?: 0.0))
                },
                label = {
                    Text(text = "Balance")
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
//                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(38.dp))
            Button(
                onClick = onSaveClick,
                modifier = Modifier.padding(start = 16.dp),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(width = 1.dp, color = Color.Green),
                // Kölgə effekti daxil edilir:
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,  // Adi halda kölgə
                    pressedElevation = 12.dp, // Kliklənəndə qalxan kölgə
                    focusedElevation = 8.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save balance"
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Save",
                        fontSize = 20.sp,
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BalanceScreenCoreScreenPreview() {
    BalanceScreenCoreScreen(
        state = BalanceState(),
        onAction = {},
        onSaveClick = {}
    )
}

