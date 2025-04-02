package org.pointyware.artes.shared.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.artes.agents.ui.NewAgentView
import org.pointyware.artes.agents.viewmodels.AgentViewModel

/**
 * The root for Compose UI.
 */
@Composable
fun ArtesApp() {

    val koin = remember { getKoin() }
    val agentViewModel = remember { koin.get<AgentViewModel>() }
    LaunchedEffect(Unit) {
        agentViewModel.loadHosts()
    }
    val state by agentViewModel.state.collectAsState()
    NewAgentView(
        state = state,
        modifier = Modifier.fillMaxSize(),
        onSelectHost = agentViewModel::onSelectHost,
        onSubmit = agentViewModel::onSave,
    )
}
