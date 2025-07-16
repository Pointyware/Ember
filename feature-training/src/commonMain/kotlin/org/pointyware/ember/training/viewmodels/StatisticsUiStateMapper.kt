package org.pointyware.ember.training.viewmodels

import org.pointyware.artes.common.Mapper
import org.pointyware.ember.training.entities.Measurement
import org.pointyware.ember.training.entities.Snapshot
import org.pointyware.ember.ui.graph.DataSeries

object StatisticsUiStateMapper: Mapper<Snapshot, StatisticsUiState> {

    private val dataSeriesMapper = DataSeriesMapper()

    override fun map(input: Snapshot): StatisticsUiState {
        val dataSeries = dataSeriesMapper.map(input.measurements)
        val max = if (dataSeries.isNotEmpty()) {
            dataSeries.maxOf { series -> series.dataPoints.maxOf { it.second } }
        } else {
            10f
        }
        val min = if (dataSeries.isNotEmpty()) {
            dataSeries.maxOf { series -> series.dataPoints.minOf { it.second } }
        } else {
            0f
        }
        return StatisticsUiState(
            epochCount = input.epoch,
            floor = min,
            ceiling = max,
            data = dataSeries
        )
    }
}

class DataSeriesMapper(
    // TODO: inject color mapper
): Mapper<Map<Measurement, List<Pair<Float, Float>>>, List<DataSeries>> {
    override fun map(input: Map<Measurement, List<Pair<Float, Float>>>): List<DataSeries> {
        var id = 0
        return input.map { (measurement, data) ->
            DataSeries(
                label = MeasurementLabelMapper.map(measurement),
                color = id++.toLong(),
                dataPoints = data,
            )
        }
    }
}

object MeasurementLabelMapper: Mapper<Measurement, String> {
    override fun map(input: Measurement): String {
        return when (input) {
            Measurement.Loss -> "Loss"
            Measurement.Accuracy -> "Accuracy"
            Measurement.Activation -> "Activation"
            Measurement.AllParameters -> "All Parameters"
            Measurement.Derivative -> "Derivative"
            Measurement.Gradient -> "Gradient"
            Measurement.Preactivation -> "Preactivation"
            Measurement.Weights -> "Weights"
        }
    }
}
