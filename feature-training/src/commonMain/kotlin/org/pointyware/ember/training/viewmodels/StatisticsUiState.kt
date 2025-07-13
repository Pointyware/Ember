package org.pointyware.ember.training.viewmodels

import org.pointyware.ember.ui.graph.DataSeries

/**
 * Represents the statistics collected during training.
 */
data class StatisticsUiState(
    val epochCount: Int,
    val floor: Float,
    val ceiling: Float,
    val data: List<DataSeries>
) {
    companion object {
        val Default = StatisticsUiState(
            epochCount = 10,
            floor = 0f,
            ceiling = 10f,
            data = emptyList()
        )
    }
}
