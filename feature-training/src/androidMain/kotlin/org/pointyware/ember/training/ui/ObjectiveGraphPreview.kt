package org.pointyware.ember.training.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.pointyware.ember.ui.graph.DataSeries

@Preview
@Composable
private fun TrainingViewPreview() {
    ObjectiveGraph(
        objectiveFloor = 0f,
        objectiveLabel = "Accuracy",
        objectiveCeiling = 10f,
        epochCount = 10,
        data = listOf(
            DataSeries(
                label = "Hello",
                color = 0L,
                dataPoints = listOf(
                    1f to 2f,
                    2f to 1f,
                    3f to 4f,
                    4f to 3f
                ),
            )
        ),
        modifier = Modifier.size(500.dp)
    )
}
