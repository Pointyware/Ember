package org.pointyware.ember.training.viewmodels

import org.pointyware.artes.common.Mapper
import org.pointyware.ember.training.entities.Statistics
import org.pointyware.ember.ui.graph.DataSeries

object StatisticsUiStateMapper: Mapper<Statistics, StatisticsUiState> {

    private val dataSeriesMapper = DataSeriesMapper()

    override fun map(input: Statistics): StatisticsUiState {
        val dataSeries = dataSeriesMapper.map(input)
        return StatisticsUiState(
            epochCount = input.epochCount,
            ceiling = input.measurementsMax,
            data = dataSeriesMapper.map(input)
        )
    }
}

class DataSeriesMapper(
    // TODO: inject color mapper
): Mapper<Statistics, List<DataSeries>> {
    override fun map(input: Statistics): List<DataSeries> {
        return input.measurements.map {
            DataSeries(
                label = it.name,
                color = 0L,
                dataPoints = input.data(it)
            )
        }
    }
}
