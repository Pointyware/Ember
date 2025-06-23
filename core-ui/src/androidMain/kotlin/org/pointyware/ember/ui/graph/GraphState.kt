package org.pointyware.ember.ui.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Defines the UI state for a 2D graph component.
 */
data class GraphState(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
    val xAxisLabel: String,
    val yAxisLabel: String,
)

fun DrawScope.drawGraph(
    state: GraphState
) {

}
