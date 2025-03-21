package org.pointyware.artes.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun colorScheme(
    darkTheme: Boolean,
    dynamicTheme: Boolean
): ColorScheme {
    return simpleColorScheme(darkTheme)
}
