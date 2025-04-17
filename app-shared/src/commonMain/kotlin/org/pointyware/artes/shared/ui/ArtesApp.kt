package org.pointyware.artes.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.artes.agents.ui.AgentListScreen
import org.pointyware.artes.viewmodels.AgentListViewModel

/**
 * The root for Compose UI.
 */
@Composable
fun ArtesApp() {

    val koin = remember { getKoin() }
    AgentListScreen(
        viewModel = remember { koin.get<AgentListViewModel>() },
    )
}
