package org.pointyware.ember.viewmodels

import androidx.compose.ui.graphics.Color

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
    val centerColor: Color = Color.Black,
    val positiveColor: Color = Color.Yellow,
    val negativeColor: Color = Color.Blue,
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
