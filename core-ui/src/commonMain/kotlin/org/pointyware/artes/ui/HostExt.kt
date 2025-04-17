package org.pointyware.artes.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import org.pointyware.artes.entities.Google
import org.pointyware.artes.entities.Host
import org.pointyware.artes.entities.None
import org.pointyware.artes.entities.OpenAi
import org.pointyware.artes.entities.SelfHosted

/**
 * Provides the localized name of a host service.
 */
@Composable
fun Host.localizedName(): String {
    return when (this) {
        OpenAi -> {
            stringResource(Res.string.title_host_open_ai)
        }
        Google -> {
            stringResource(Res.string.title_host_google)
        }
        is SelfHosted -> {
            stringResource(Res.string.title_host_self_hosted, this.url)
        }
        None -> {
            stringResource(Res.string.title_host_none)
        }
        else -> {
            stringResource(Res.string.title_host_unsupported)
        }
    }
}
