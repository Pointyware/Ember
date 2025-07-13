package org.pointyware.ember.ui.graph

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer


/**
 *
 */
data class LineGraphState(
    val graphState: GraphState,
    val series: List<DataSeries>,
)

/**
 *
 */
fun DrawScope.drawLineGraph(
    state: LineGraphState,
    textMeasurer: TextMeasurer
) {
    drawGraph(
        state = state.graphState,
        textMeasurer = textMeasurer
    ) {
        state.series.forEach {
            plotSeries(it.dataPoints)
        }
    }
}
