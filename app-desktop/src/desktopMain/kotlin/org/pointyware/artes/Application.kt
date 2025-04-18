package org.pointyware.artes

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.stringResource
import org.koin.core.context.startKoin
import org.pointyware.artes.shared.di.sharedModule
import org.pointyware.artes.shared.ui.ArtesApp
import org.pointyware.artes.ui.Res
import org.pointyware.artes.ui.theme.ArtesTheme
import org.pointyware.artes.ui.title_app

/**
 *
 */
fun main(vararg args: String) = application {

    startKoin {
        modules(
            sharedModule()
        )
    }

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
        title = stringResource(Res.string.title_app),
        state = windowState,
    ) {
        ArtesTheme {
            ArtesApp()
        }
    }
}
