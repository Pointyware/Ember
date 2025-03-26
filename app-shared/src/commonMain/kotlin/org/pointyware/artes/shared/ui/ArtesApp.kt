package org.pointyware.artes.shared.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.artes.hosts.ui.NewHostView
import org.pointyware.artes.text.completion.CompletionView
import org.pointyware.artes.text.completion.CompletionViewModel
import org.pointyware.artes.text.completion.CompletionViewState

/**
 *
 */
@Composable
fun ArtesApp() {
    NewHostView(
        modifier = Modifier.fillMaxSize(),
        onHostCreated = {}
    )
}
