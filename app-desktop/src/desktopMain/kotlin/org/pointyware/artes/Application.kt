package org.pointyware.artes

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.pointyware.artes.shared.ui.ArtesApp

/**
 *
 */
fun main(vararg args: String) = application {

    with(args.iterator()) {
        var exit = false
        val commandList = listOf<Command>(
            lenientCommand("--version") {
                println("Artes-Desktop v0.1.0")
                exit = true
            }
        )
        while (hasNext()) {
            if (exit) return@application
            val argument = next()
            println("Processing arg: $argument")
            commandList.forEach {
                if (it.matches(argument)) {
                    it.accept(this)
                    return@forEach
                }
            }
        }
    }

    val windowState = rememberWindowState()
    Window(
        onCloseRequest = ::exitApplication,
        title = "",
        state = windowState,
    ) {
        ArtesApp()
    }
}
