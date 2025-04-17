package org.pointyware.artes.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.artes.agents.ui.AgentEditorScreen
import org.pointyware.artes.agents.viewmodels.AgentEditorViewModel

/**
 * The root for Compose UI.
 */
@Composable
fun ArtesApp() {

    val koin = remember { getKoin() }
    AgentEditorScreen(
        viewModel = remember { koin.get<AgentEditorViewModel>() },
    )
}
