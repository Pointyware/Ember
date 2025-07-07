package org.pointyware.ember.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import org.pointyware.ember.viewmodels.ColorMap
import org.pointyware.ember.viewmodels.DefaultColorMap

/**
 * Displays a single neuron in a neural network layer.
 * The neuron is represented by its weights and bias
 * rendered as colored squares.
 */
@Composable
fun NeuronView(
    weights: List<Float>,
    bias: Float,
    modifier: Modifier = Modifier,
    squareSize: Float = 20f,
    colorMap: ColorMap = DefaultColorMap
) {
    Canvas(
        modifier = modifier
            .width(((weights.size + 1) * squareSize).dp) // Adjust width based on number of weights
            .height(squareSize.dp) // Fixed height for the neuron view
    ) {
        fun drawSquare(value: Float, index: Int) {
            val color = colorMap.getColor(value)
            drawRect(
                color = color,
                topLeft = Offset(index * squareSize, 0f),
                size = Size(squareSize, squareSize)
            )
        }
        // Draw each weight as a colored square
        weights.forEachIndexed { index, weight ->
            drawSquare(weight, index)
        }
        drawSquare(bias, weights.size)
    }
}
