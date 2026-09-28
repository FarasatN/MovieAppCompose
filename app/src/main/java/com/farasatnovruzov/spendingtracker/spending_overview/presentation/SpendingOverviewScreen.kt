package com.farasatnovruzov.spendingtracker.spending_overview.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.spendingtracker.core.presentation.ui.theme.SpendingTrackerAppComposeTheme
import com.farasatnovruzov.spendingtracker.core.presentation.util.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpendingOverviewScreenCore(
    viewModel: SpendingOverviewViewModel = koinViewModel(),
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
//    onDeleteSpendingClick: (Int) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.onAction(SpendingOverviewAction.LoadSpendingOverviewBalance)
    }
    SpendingOverviewScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBalanceClick = onBalanceClick,
        onAddSpendingClick = onAddSpendingClick,
        onDeleteSpendingClick = {
            viewModel.onAction(SpendingOverviewAction.OnDeleteSpending(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SpendingOverviewScreen(
    state: SpendingOverviewState,
    onAction: (SpendingOverviewAction) -> Unit,
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
    onDeleteSpendingClick: (Int) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column() {
                SpendingOverviewTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    balance = state.balance,
                    onBalanceClick = onBalanceClick,
                    scrollBehavior = scrollBehavior
                )
                Spacer(modifier = Modifier.height(8.dp))
                DatePickerDropDownMenu(
                    state = state,
                    onItemClick = { index ->
                        onAction(SpendingOverviewAction.OnDateChange(index))
                    },
                    modifier = Modifier.padding(24.dp)
                )
            }
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { onAddSpendingClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Spending"
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    ) { paddingValues ->
        Background()
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

        }
    }
}

@Composable
fun DatePickerDropDownMenu(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState = SpendingOverviewState(),
    onItemClick: (Int) -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier
            .shadow(
                elevation = 0.5.dp,
                shape = RoundedCornerShape(16.dp)
            )
    ){
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.heightIn(max = 440.dp)
        ) { }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingOverviewTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    balance: Double,
    onBalanceClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "$${balance}",
                fontSize = 35.sp,
                maxLines = 1,
                fontFamily = FontFamily.Monospace,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
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
                    .clickable { onBalanceClick() },
                contentAlignment = androidx.compose.ui.Alignment.Center,
            ) {
                Text(
                    text = "$",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace
                )
            }
        },
        modifier = modifier.padding(end = 16.dp, start = 12.dp),
    )

}

@Preview
@Composable
private fun ScreenPreview() {
    SpendingTrackerAppComposeTheme {
        SpendingOverviewScreen(
            state = SpendingOverviewState(),
            onAction = {},
            onBalanceClick = {},
            onAddSpendingClick = {},
            onDeleteSpendingClick = {},
        )
    }
}