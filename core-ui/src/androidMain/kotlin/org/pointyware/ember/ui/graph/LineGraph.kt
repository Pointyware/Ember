package org.pointyware.ember.ui.graph

import androidx.compose.ui.graphics.drawscope.DrawScope


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
    state: LineGraphState
) {

}
