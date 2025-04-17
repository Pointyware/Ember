package org.pointyware.artes.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import org.pointyware.artes.entities.Google
import org.pointyware.artes.entities.Host
import org.pointyware.artes.entities.OpenAi

/**
 * Provides the localized name of a host service.
 */
@Composable
fun Host.localizedName(): String {
    return when (this) {
        is OpenAi -> {
            stringResource(Res.string.title_host_open_ai)
        }
        is Google -> {
            stringResource(Res.string.title_host_google)
        }
        else -> {
            stringResource(Res.string.title_host_unsupported)
        }
    }
}
