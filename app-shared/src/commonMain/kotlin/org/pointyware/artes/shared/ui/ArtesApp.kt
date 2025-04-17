package org.pointyware.artes.shared.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * The root for Compose UI.
 */
@Composable
fun ArtesApp() {

    AgentServiceNavigation(
        modifier = Modifier.fillMaxSize()
    )
}
