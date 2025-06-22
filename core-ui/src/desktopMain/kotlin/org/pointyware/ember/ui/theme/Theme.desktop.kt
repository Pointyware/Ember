package org.pointyware.ember.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import org.pointyware.ember.ui.theme.simpleColorScheme

@Composable
actual fun colorScheme(
    darkTheme: Boolean,
    dynamicTheme: Boolean
): ColorScheme {
    return simpleColorScheme(darkTheme)
}
