package org.pointyware.artes.agents.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.pointyware.artes.ui.AgentInfoView
import org.pointyware.artes.ui.AgentInfoViewState
import org.pointyware.artes.viewmodels.DefaultAgentInfoViewModel

@Composable
fun AgentInfoScreen(
    viewModel: DefaultAgentInfoViewModel,
    onEditAgent: (Long) -> Unit,
    onBack: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val state by viewModel.state.collectAsState()
        AgentInfoView(
            state = state.let {
                AgentInfoViewState(
                    name = it.name,
                    description = "Model: ${it.model.name}",
                    hostConfig = it.host
                )
            },
            modifier = Modifier.fillMaxSize(),
            onDelete = viewModel::onDelete,
            onEdit = { onEditAgent(state.id) }
        )
    }
}
