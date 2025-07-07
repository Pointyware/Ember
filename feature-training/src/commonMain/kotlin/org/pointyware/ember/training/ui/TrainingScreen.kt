package org.pointyware.ember.training.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.pointyware.ember.training.viewmodels.TrainingUiState
import org.pointyware.ember.training.viewmodels.TrainingViewModel
import org.pointyware.ember.ui.NeuralNetworkView

/**
 * The Training Screen
 */
@Composable
fun TrainingScreen(
    viewModel: TrainingViewModel,
) {
    val state by viewModel.state.collectAsState()
    TrainingView(
        state = state,
        onStart = viewModel::onStart,
        onStop = viewModel::onStop,
        onReset = viewModel::onReset,
        onRun = viewModel::onRun,
    )
}
