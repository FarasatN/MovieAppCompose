package com.farasatnovruzov.spendingtracker.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farasatnovruzov.spendingtracker.balance.presentation.BalanceScreenCore
import com.farasatnovruzov.spendingtracker.core.presentation.ui.theme.SpendingTrackerAppComposeTheme
import com.farasatnovruzov.spendingtracker.core.presentation.util.Background
import com.farasatnovruzov.spendingtracker.core.presentation.util.Screen
import com.farasatnovruzov.spendingtracker.spending_details.presentation.SpendingDetailsScreenCore
import com.farasatnovruzov.spendingtracker.spending_overview.presentation.SpendingOverviewScreenCore

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpendingTrackerAppComposeTheme {
                Navigation(modifier = Modifier.fillMaxSize())
            }
        }
    }


    @Composable
    fun Navigation(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        Background(modifier = modifier)
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screen.SpendingOverview,
        ){
            composable<Screen.SpendingOverview>{
                SpendingOverviewScreenCore(
                    onBalanceClick = {
                        navController.navigate(Screen.Balance)
                    },
                    onAddSpendingClick = {
                        navController.navigate(Screen.SpendingDetails)
                    }
                )
            }
            composable<Screen.SpendingDetails>{

//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = androidx.compose.ui.Alignment.Center
//                ){
//                    Text(text = "Spending Details")
//                }

                SpendingDetailsScreenCore(
                    onSaveSpending = {
                        navController.popBackStack()
                    }
                )
            }
            composable<Screen.Balance>{
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = androidx.compose.ui.Alignment.Center
//                ){
//                    Text(text = "Balance")
//                }
                BalanceScreenCore(
                    onSaveClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
