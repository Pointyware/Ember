package org.pointyware.ember.ui.graph

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText

/**
 * Defines the UI state for a 2D graph component.
 */
data class GraphState(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
    val xAxisLabel: String,
    val yAxisLabel: String,
)

/**
 * Provides mapping functions between graph space and pixel space.
 */
data class GraphSpaceMap(
    val leftPadding: Float,
    val bottomPadding: Float,
    val state: GraphState,
    val drawScope: DrawScope
) {

    fun plotSeries(data: List<Pair<Float, Float>>) {
        val width = drawScope.size.width
        val height = drawScope.size.height

        val path = Path()
        for (i in 0 until data.size - 1) {
            val start = data[i]

            val x = xToPixel(start.first, width)
            val y = yToPixel(start.second, height)

            if (i == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        drawScope.drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(2f, cap = Stroke.DefaultCap)
        )
    }

    fun xToPixel(x: Float, width: Float): Float {
        return leftPadding + (x - state.left) / (state.right - state.left) * (width - leftPadding)
    }
    fun yToPixel(y: Float, height: Float): Float {
        return (y - state.bottom) / (state.top - state.bottom) * (height - bottomPadding)
    }
}

/**
 * Draws a 2D graph with horizontal and vertical axes.
 * The horizontal axis is drawn at the bottom of the graph, and the vertical
 * axis is drawn at the left side of the graph.
 */
fun DrawScope.drawGraph(
    state: GraphState,
    textMeasurer: TextMeasurer,
    content: GraphSpaceMap.()->Unit,
) {
    val leftPadding = 40f
    val bottomPadding = 40f
    // Draw Horizontal Axis and Label
    drawLine(
        color = Color.Black,
        start = Offset(leftPadding, size.height - bottomPadding),
        end = Offset(size.width, size.height - bottomPadding),
    )
    drawText(
        textMeasurer = textMeasurer,
        text = state.xAxisLabel,
        topLeft = Offset(size.width / 2, size.height - bottomPadding)
    )
    drawText(
        textMeasurer = textMeasurer,
        text = state.left.toString(),
        topLeft = Offset(x = 0f, y = size.height - bottomPadding)
    )
    drawText(
        textMeasurer = textMeasurer,
        text = state.right.toString(),
        topLeft = Offset(x = size.width - leftPadding, y = size.height - bottomPadding)
    )

    // Draw Vertical Axis and Label
    drawLine(
        color = Color.Black,
        start = Offset(leftPadding, 0f),
        end = Offset(leftPadding, size.height - bottomPadding),
    )
    withTransform({
        rotate(
            degrees = -90f,
            pivot = Offset(0f, size.height - bottomPadding)
        )
    }) {
        drawText(
            textMeasurer = textMeasurer,
            text = state.yAxisLabel,
            topLeft = Offset(size.height / 2, size.height - bottomPadding),
            size = Size(size.width, 1000f),
        )
    }
    drawText(
        textMeasurer = textMeasurer,
        text = state.bottom.toString(),
        topLeft = Offset(x = leftPadding, y = size.height - bottomPadding)
    )
    drawText(
        textMeasurer = textMeasurer,
        text = state.top.toString(),
        topLeft = Offset(x = leftPadding, y = 0f)
    )

    // Draw Data Series
    val graphSpaceMap = GraphSpaceMap(leftPadding, bottomPadding, state, this)
    graphSpaceMap.content()
}
