package org.pointyware.ember.ui.graph

/**
 * A single series of data points to be plotted on a graph.
 *
 * @see [drawLineGraph]
 * @see [drawScatterPlot]
 */
data class DataSeries(
    val label: String,
    val color: Long,
    val dataPoints: List<Pair<Float, Float>>,
)
