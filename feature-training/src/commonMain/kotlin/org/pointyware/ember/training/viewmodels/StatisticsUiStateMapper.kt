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
): Mapper<Map<Measurement<Float>, Map<Float, Float>>, List<DataSeries>> {
    override fun map(input: Map<Measurement<Float>, Map<Float, Float>>): List<DataSeries> {
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

object MeasurementLabelMapper: Mapper<Measurement<Float>, String> {
    override fun map(input: Measurement<Float>): String {
        return when (input) {
            is Measurement.Given<*> -> input.label
            is Measurement.Intermediate<*> -> input.label
            is Measurement.Analytical<*> -> input.label
            else -> "Deprecated" // TODO: remove after deprecation
        }
    }
}
