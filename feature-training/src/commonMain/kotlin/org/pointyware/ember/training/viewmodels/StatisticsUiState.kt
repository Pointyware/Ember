package org.pointyware.ember.training.viewmodels

import org.pointyware.ember.ui.graph.DataSeries

/**
 * Represents the statistics collected during training.
 */
data class StatisticsUiState(
    val objectiveName: String,
    val epochCount: Int,
    val ceiling: Float,
    val data: List<DataSeries>
) {
    companion object {
        val Default = StatisticsUiState(
            objectiveName = "Placeholder",
            epochCount = 10,
            ceiling = 10f,
            data = emptyList()
        )
    }
}
