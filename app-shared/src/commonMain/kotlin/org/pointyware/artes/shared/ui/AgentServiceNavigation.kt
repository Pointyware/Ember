package org.pointyware.artes.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.artes.agents.ui.AgentEditorView
import org.pointyware.artes.agents.ui.AgentListScreen
import org.pointyware.artes.agents.viewmodels.AgentEditorViewModel
import org.pointyware.artes.entities.OpenAi
import org.pointyware.artes.navigation.Destination
import org.pointyware.artes.ui.AgentInfoView
import org.pointyware.artes.ui.AgentInfoViewState
import org.pointyware.artes.ui.ServiceListView
import org.pointyware.artes.viewmodels.AgentInfoViewModel
import org.pointyware.artes.viewmodels.AgentListViewModel
import org.pointyware.artes.viewmodels.HostUiState
import org.pointyware.artes.viewmodels.ServiceListViewModel


/**
 *
 */
@Composable
fun AgentServiceNavigation(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val destination by navController.currentBackStackEntryFlow.collectAsState(Destination.AgentList)
    when (destination) {
        Destination.AgentList -> {
            val koin = remember { getKoin() }
            val agentListViewModel: AgentListViewModel = remember { koin.get() }
            AgentListScreen(
                viewModel = agentListViewModel,
            )
        }
        Destination.AgentInfo -> {
            val agentInfoViewModel: AgentInfoViewModel = remember { getKoin().get() }
            AgentInfoView(
                state = AgentInfoViewState(
                    name = "Sam",
                    description = "A helpful agent",
                    service = HostUiState(
                        id = 0L,
                        title = "Dev - OpenAI",
                        host = OpenAi
                    )
                ),
                modifier = modifier,
                onDelete = agentInfoViewModel::onDelete,
                onEdit = agentInfoViewModel::onEdit
            )
        }
        Destination.AgentEditor -> {
            val agentEditorViewModel: AgentEditorViewModel = remember { getKoin().get() }
            AgentEditorView(
                state = agentEditorViewModel.state.collectAsState().value,
                modifier = modifier,
                onSelectHost = agentEditorViewModel::onSelectHost,
                onSubmit = agentEditorViewModel::onSave
            )
        }
        Destination.ServiceList -> {
            val koin = remember { getKoin() }
            val serviceListViewModel = remember { koin.get<ServiceListViewModel>() }
            val state by serviceListViewModel.state.collectAsState()
            ServiceListView(
                state = state,
                modifier = modifier,
                onRegisterService = { TODO("Navigate to New Host") }
            )
        }
    }
}
