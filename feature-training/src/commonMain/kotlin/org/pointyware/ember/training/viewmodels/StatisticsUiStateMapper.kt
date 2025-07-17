package org.pointyware.ember.training.viewmodels

import org.pointyware.artes.common.Mapper
import org.pointyware.ember.training.entities.Measurement
import org.pointyware.ember.training.entities.Snapshot
import org.pointyware.ember.ui.graph.DataSeries

object StatisticsUiStateMapper: Mapper<Snapshot, StatisticsUiState> {

    private val dataSeriesMapper = DataSeriesMapper()

    override fun map(input: Snapshot): StatisticsUiState {
        val dataSeries = dataSeriesMapper.map(input.measurements)
        return StatisticsUiState(
            epochCount = input.epoch,
            floor = input.floor,
            ceiling = input.ceiling,
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
