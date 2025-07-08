package org.pointyware.ember.training.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.pointyware.ember.entities.activations.ReLU
import org.pointyware.ember.training.viewmodels.StatisticsUiState
import org.pointyware.ember.training.viewmodels.TrainingUiState
import org.pointyware.ember.ui.theme.EmberTheme
import org.pointyware.ember.viewmodels.DefaultColorMap
import org.pointyware.ember.viewmodels.LayerUiState
import org.pointyware.ember.viewmodels.NeuralNetworkUiState

@Preview
@Composable
private fun TrainingViewPreview() {
    ObjectiveGraph(
        objectiveLabel = "Accuracy",
        objectiveCeiling = 10f,
        epochCount = 10,
        data = emptyList(),
        modifier = Modifier.size(500.dp)
    )
}
