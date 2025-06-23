package org.pointyware.ember.ui.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun LineGraphPreview(

) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawLineGraph(
            state = LineGraphState(
                graphState = GraphState(
                    bottom = 0f,
                    left = 0f,
                    top = 20f,
                    right = 10f,
                    xAxisLabel = "X Axis",
                    yAxisLabel = "Y Axis"
                ),
                series = emptyList()
            )
        )
    }
}

@Preview
@Composable
private fun ScatterPlotPreview(

) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawScatterPlot(
            state = ScatterPlotState(
                graphState = GraphState(
                    bottom = 0f,
                    left = 0f,
                    top = 20f,
                    right = 10f,
                    xAxisLabel = "X Axis",
                    yAxisLabel = "Y Axis"
                ),
                series = emptyList()
            )
        )
    }
}
