package org.pointyware.artes.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalGeometry =
    staticCompositionLocalOf<Geometry> {
        error("No geometry provided")
    }

val DefaultLocalGeometry = Geometry(
    4.dp, 8.dp, 16.dp
)

data class Geometry(
    val paddingSmall: Dp,
    val paddingMedium: Dp,
    val paddingLarge: Dp,
)

/**
 * Defines the shapes used in the Artes UI.
 */
val artesShapes = Shapes(
    small = RoundedCornerShape(DefaultLocalGeometry.paddingSmall)
)
