package com.farasatnovruzov.movieappcompose.screens.weather.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.model.weather.local.Unit
import com.farasatnovruzov.movieappcompose.ui.theme.SkyBlue
import com.farasatnovruzov.movieappcompose.widgets.weather.WeatherAppBar

@Composable
fun SettingsScreen(
    navController: NavController, settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val measurementUnits = listOf("Metric (C)", "Imperial (F)")
    val choiceFromDb by settingsViewModel.unitList.collectAsState()
    val dbUnit = choiceFromDb.lastOrNull()?.unit ?: measurementUnits[0]
    var unitToggleState by rememberSaveable(dbUnit) { mutableStateOf(dbUnit.contains("Imperial")) }
    val choiceState = if (unitToggleState) measurementUnits[1] else measurementUnits[0]

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Settings",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                isMainScreen = false,
                navController = navController,
                onButtonClicked = {
                    navController.popBackStack()
                })
        }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = unitToggleState,
                    onCheckedChange = {
                        unitToggleState = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(
                            SkyBlue
                        )
                ) {
                    Text(
                        text = if (unitToggleState) "Fahrenheit ºF" else "Celsius ºC",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Log.d("choiceState: ", "choiceState: $choiceState")
                Button(
                    onClick = {
                        settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnit(Unit(choiceState))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green.copy(.3f)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 7.dp,
                        pressedElevation = 1.dp,
                        disabledElevation = 0.dp,
                        hoveredElevation = 10.dp,
                        focusedElevation = 10.dp
                    )
                ) {
                    Text(
                        "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}