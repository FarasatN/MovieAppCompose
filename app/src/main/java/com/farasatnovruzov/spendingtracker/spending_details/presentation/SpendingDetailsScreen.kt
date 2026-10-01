package com.farasatnovruzov.spendingtracker.spending_details.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily.Companion.Monospace
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.spendingtracker.core.presentation.util.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpendingDetailsScreenCore(
    spendingId: Int? = -1,
    viewModel: SpendingDetailsViewModel = koinViewModel(),
    onSaveSpending: () -> Unit,
) {
    LaunchedEffect(key1 = spendingId) {
        viewModel.loadSpending(spendingId)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                SpendingDetailsEvent.SaveSuccess -> onSaveSpending()
                SpendingDetailsEvent.SaveFailed -> {
                    Toast.makeText(
                        context,
                        "Error, make sure to enter valid spending info.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    SpendingDetailsScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        isEditing = spendingId != null && spendingId != -1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingDetailsScreen(
    state: SpendingDetailsState,
    onAction: (SpendingDetailsAction) -> Unit,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(end = 8.dp, start = 16.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = if (isEditing) "Edit Spending" else "Add Spending",
                        fontFamily = Monospace,
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(13.dp)
                            )
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                            .clickable { onAction(SpendingDetailsAction.SaveSpending) },
                        contentAlignment = androidx.compose.ui.Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save Spending",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Background()
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedTextField(
                value = state.name,
                onValueChange = { onAction(SpendingDetailsAction.UpdateName(it)) },
                label = { Text(text = "Name", fontWeight = FontWeight.Medium) },
                textStyle = TextStyle(
                    fontFamily = Monospace,
                    fontSize = 17.sp,
                ),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = if (state.price == 0.0) "" else state.price.toString(),
                onValueChange = { onAction(SpendingDetailsAction.UpdatePrice(it.toDoubleOrNull() ?: 0.0)) },
                label = { Text(text = "Price", fontWeight = FontWeight.Medium) },
                textStyle = TextStyle(
                    fontFamily = Monospace,
                    fontSize = 17.sp,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                OutlinedTextField(
                    value = if (state.kilograms == 0.0) "" else state.kilograms.toString(),
                    onValueChange = {
                        onAction(
                            SpendingDetailsAction.UpdateKilograms(
                                it.toDoubleOrNull() ?: 0.0
                            )
                        )
                    },
                    label = { Text(text = "Kilograms", fontWeight = FontWeight.Medium) },
                    textStyle = TextStyle(
                        fontFamily = Monospace,
                        fontSize = 17.sp,
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = if (state.quantity == 0.0) "" else state.quantity.toString(),
                    onValueChange = {
                        onAction(
                            SpendingDetailsAction.UpdateQuantity(
                                it.toDoubleOrNull() ?: 0.0
                            )
                        )
                    },
                    label = { Text(text = "Quantity", fontWeight = FontWeight.Medium) },
                    textStyle = TextStyle(
                        fontFamily = Monospace,
                        fontSize = 17.sp,
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .weight(1f)
                )

            }
        }

    }

}