package com.farasatnovruzov.movieappcompose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.bytebeats.views.charts.bar.BarChart
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.bar.SimpleBarDrawer
import me.bytebeats.views.charts.bar.render.label.SimpleLabelDrawer
import me.bytebeats.views.charts.bar.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.bar.render.yaxis.SimpleYAxisDrawer

@Composable
fun SpendingGraph(modifier: Modifier) {
    Text(
        text = "Spending Statistics",
        fontFamily = PlayFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))

    Chart()
}

@Composable
fun Chart(modifier: Modifier = Modifier) {
    BarChart(
        modifier = modifier,
        barChartData = BarChartData(spendingByDay),
        xAxisDrawer = SimpleXAxisDrawer(
            axisLineThickness = 2.dp,
            axisLineColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        ),
        yAxisDrawer = SimpleYAxisDrawer(
            labelValueFormatter = {"$ ${it.toInt()}"},
            axisLineThickness = 2.dp,
            axisLineColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        ),
        labelDrawer = SimpleLabelDrawer(
            drawLocation = SimpleLabelDrawer.DrawLocation.Outside,
            labelTextSize = 15.sp,

        ),
        barDrawer = SimpleBarDrawer()
    )
}


val spendingByDay = listOf(
    BarChartData.Bar(
        label = "Dec 1", value = 123f, color = randomColor()
    ),
    BarChartData.Bar(
        label = "Dec 2", value = 140f, color = randomColor()
    ),
    BarChartData.Bar(
        label = "Dec 3", value = 335f, color = randomColor()
    ),

    )