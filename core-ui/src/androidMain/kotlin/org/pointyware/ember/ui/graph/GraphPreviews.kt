package org.pointyware.ember.ui.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val square = Modifier.size(400.dp, 400.dp)

@Preview
@Composable
private fun GraphPreview(

) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = square
    ) {
        drawGraph(
            state = GraphState(
                bottom = -1f,
                left = -1f,
                top = 5f,
                right = 7f,
                xAxisLabel = "X Axis",
                yAxisLabel = "Y Axis"
            ),
            textMeasurer = textMeasurer
        ) {
            plotSeries(
                listOf(
                    0f to 0f,
                    1f to 2f,
                    2f to 1f,
                    3f to 3f,
                    4f to 2f,
                    5f to 1f,
                    6f to 4f,
                )
            )
        }
    }
}


@Preview
@Composable
private fun LineGraphPreview(

) {
    // TODO: sample loss data from training for preview
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = square
    ) {
        drawLineGraph(
            state = LineGraphState(
                graphState = GraphState(
                    bottom = 0f,
                    left = 0f,
                    top = 20f,
                    right = 10f,
                    xAxisLabel = "Time (t)",
                    yAxisLabel = "Parameter Value"
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
        modifier = square
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
