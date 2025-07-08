package org.pointyware.ember.training.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.pointyware.ember.training.viewmodels.TrainingViewModel

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
