package org.pointyware.ember.training.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import org.pointyware.ember.ui.graph.DataSeries
import org.pointyware.ember.ui.graph.GraphState
import org.pointyware.ember.ui.graph.LineGraphState
import org.pointyware.ember.ui.graph.drawLineGraph

/**
 *
 */
@Composable
fun ObjectiveGraph(
    objectiveLabel: String,
    objectiveCeiling: Float,
    objectiveFloor: Float,
    epochCount: Int,
    data: List<DataSeries>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
    ) {
        drawLineGraph(
            state = LineGraphState(
                graphState = GraphState(
                    left = 0f,
                    bottom = objectiveFloor,
                    top = objectiveCeiling,
                    right = epochCount.toFloat(),
                    xAxisLabel = "Epochs",
                    yAxisLabel = objectiveLabel
                ),
                series = data,
            ),
            textMeasurer = textMeasurer
        )
    }
}
