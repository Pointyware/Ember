package org.pointyware.ember

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.stringResource
import org.koin.core.context.startKoin
import org.pointyware.ember.ui.EmberApp
import org.pointyware.ember.shared.di.sharedModule
import org.pointyware.ember.ui.theme.EmberTheme
import org.pointyware.ember.ui.title_app
import org.pointyware.ember.ui.Res as UiRes

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
                println("Ember-Desktop v0.1.0")
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
        title = stringResource(UiRes.string.title_app),
        state = windowState,
    ) {
        EmberTheme {
            EmberApp()
        }
    }
}
