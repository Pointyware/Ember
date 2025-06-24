package org.pointyware.ember.ui.graph

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer

/**
 *
 */
data class ScatterPlotState(
    val graphState: GraphState,
    val series: List<DataSeries>,
)

/**
 * Draws a scatter plot.
 */
fun DrawScope.drawScatterPlot(
    state: ScatterPlotState,
    textMeasurer: TextMeasurer
) {
    drawGraph(
        state = state.graphState,
        textMeasurer = textMeasurer
    ) { context ->

    }
}
