package org.pointyware.ember.ui.graph

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
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
    val state: GraphState
) {
    fun xToPixel(x: Float, width: Float): Float {
        return (x - state.left) / (state.right - state.left) * width
    }
    fun yToPixel(y: Float, height: Float): Float {
        return height - (y - state.bottom) / (state.top - state.bottom) * height
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
    content: DrawScope.(GraphSpaceMap)->Unit,
) {
    val leftPadding = 20f
    val bottomPadding = 20f
    // Draw Horizontal Axis and Label
    drawLine(
        color = Color.Black,
        start = Offset(leftPadding, size.height - bottomPadding),
        end = Offset(size.width, size.height - bottomPadding),
    )
    drawText(
        textMeasurer = textMeasurer,
        text = state.xAxisLabel,
        topLeft = Offset(size.width - leftPadding, size.height - bottomPadding)
    )

    // Draw Vertical Axis and Label
    drawLine(
        color = Color.Black,
        start = Offset(leftPadding, 0f),
        end = Offset(leftPadding, size.height - bottomPadding),
    )
    withTransform({
        rotate(degrees = -90f, pivot = Offset(0f, size.height - bottomPadding))
    }) {
        drawText(
            textMeasurer = textMeasurer,
            text = state.yAxisLabel,
            topLeft = Offset(-size.height + bottomPadding, 0f)
        )
    }

    // Draw Data Series
    clipRect(
        left = leftPadding,
        right = size.width,
        bottom = size.height - bottomPadding,
        top = 0f
    ) {
        content(GraphSpaceMap(state))
    }
    val contentWidth = size.width - leftPadding
    val graphWidth = state.right - state.left
    val contentHeight = size.height - bottomPadding
    val graphHeight = state.top - state.bottom
    withTransform({
        translate(left = state.left, top = state.top)
        scale(
            scaleX = contentWidth / graphWidth,
            scaleY = contentHeight / graphHeight
        )
        clipRect(
            left = state.left,
            right = state.right,
            bottom = state.bottom,
            top = state.top
        )
//        clipRect(
//            left = leftPadding,
//            right = size.width,
//            bottom = size.height - bottomPadding,
//            top = 0f
//        )
    }) {

        drawRect(
            color = Color.Blue,
            topLeft = Offset(-20f, -20f),
            size = Size(40f, 40f)
        )
        content(GraphSpaceMap(state))
    }
}
