package org.pointyware.ember.ui.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun GraphPreview(

) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawGraph(
            state = GraphState(
                bottom = 0f,
                left = 0f,
                top = 20f,
                right = 10f,
                xAxisLabel = "X Axis",
                yAxisLabel = "Y Axis"
            ),
            textMeasurer = textMeasurer
        ) { context ->
            drawLine(
                color = Color.Red,
                start = Offset(0f, 10f),
                end = Offset(10f, 20f)
            )
        }
    }
}


@Preview
@Composable
private fun LineGraphPreview(

) {
    val textMeasurer = rememberTextMeasurer()
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
            ),
            textMeasurer = textMeasurer
        )
    }
}

@Preview
@Composable
private fun ScatterPlotPreview(

) {
    val textMeasurer = rememberTextMeasurer()
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
            ),
            textMeasurer = textMeasurer
        )
    }
}
