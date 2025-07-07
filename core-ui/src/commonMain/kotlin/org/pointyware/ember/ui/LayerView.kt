package org.pointyware.ember.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pointyware.ember.viewmodels.LayerUiState


/**
 * Displays a single linear layer of a neural network. Neurons are displayed
 * from left to right, with the input on the top and the output on the bottom.
 */
@Composable
fun LayerView(
    state: LayerUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
    ) {
        ActivationFunctionIndicatorView(state.activationFunction)
        Column {
            for (index in state.weights.indices) {
                NeuronView(
                    weights = state.weights[index],
                    bias = state.biases[index]
                )
            }
        }
    }
}

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
        modifier = modifier.width(((weights.size + 1) * 20).dp) // Adjust width based on number of weights
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

fun interface ColorMap {
    fun getColor(value: Float): Color
}

object DefaultColorMap : ColorMap {
    override fun getColor(value: Float): Color {
        return when {
            value > 0 -> Color.Green
            value < 0 -> Color.Red
            else -> Color.Gray
        }
    }
}

data class CenteredColorMap(
    val magnitudeClip: Float = 1.0f,
    val centerColor: Color = Color.Gray,
    val positiveColor: Color = Color.Green,
    val negativeColor: Color = Color.Red,
): ColorMap {
    init {
        require(magnitudeClip > 0) { "Magnitude clip must be greater than 0" }
        require(centerColor != positiveColor && centerColor != negativeColor) {
            "Center color must be different from positive and negative colors"
        }
        require(positiveColor != negativeColor) { "Positive and negative colors must be different" }
    }

    private fun interpolateColor(start: Color, end: Color, fraction: Float): Color {
        return Color(
            red = start.red + (end.red - start.red) * fraction,
            green = start.green + (end.green - start.green) * fraction,
            blue = start.blue + (end.blue - start.blue) * fraction,
            alpha = start.alpha + (end.alpha - start.alpha) * fraction
        )
    }

    override fun getColor(value: Float): Color {
        val clippedValue = value.coerceIn(-magnitudeClip, magnitudeClip)
        val normValue = clippedValue / magnitudeClip
        return when {
            clippedValue > 0 -> interpolateColor(centerColor, positiveColor, normValue)
            clippedValue < 0 -> interpolateColor(centerColor, negativeColor, -normValue)
            else -> centerColor
        }
    }
}
