package org.pointyware.artes

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

/**
 *
 */
fun main(vararg args: String) = application {

    val windowState = rememberWindowState()
    Window(
        onCloseRequest = ::exitApplication,
        title = "",
        state = windowState,
    ) {

    }
}
