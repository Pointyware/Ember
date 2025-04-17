package org.pointyware.artes.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.artes.agents.ui.AgentEditorView
import org.pointyware.artes.agents.ui.AgentListScreen
import org.pointyware.artes.agents.viewmodels.AgentEditorViewModel
import org.pointyware.artes.entities.OpenAi
import org.pointyware.artes.hosts.ui.HostEditorView
import org.pointyware.artes.hosts.viewmodels.HostViewModel
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
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.AgentList.name,
        modifier = modifier
    ) {
        composable(Destination.AgentList.name) {
            val koin = remember { getKoin() }
            val agentListViewModel: AgentListViewModel = remember { koin.get() }
            AgentListScreen(
                viewModel = agentListViewModel,
                onNewAgent = {
                    navController.navigate(Destination.AgentEditor.name)
                },
                onShowAgent = { agent ->
                    navController.navigate(Destination.AgentInfo.name) // TODO: pass agent id with type-safe args
                },
            )
        }
        composable(Destination.AgentInfo.name) {
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
        composable(Destination.AgentEditor.name) {
            val agentEditorViewModel: AgentEditorViewModel = remember { getKoin().get() }
            AgentEditorView(
                state = agentEditorViewModel.state.collectAsState().value,
                modifier = modifier,
                onSelectHost = agentEditorViewModel::onSelectHost,
                onSubmit = agentEditorViewModel::onSave
            )
        }
        composable(Destination.ServiceList.name) {
            val koin = remember { getKoin() }
            val serviceListViewModel = remember { koin.get<ServiceListViewModel>() }
            val state by serviceListViewModel.state.collectAsState()
            ServiceListView(
                state = state,
                modifier = modifier,
                onRegisterService = {
                    navController.navigate(Destination.ServiceEditor.name)
                }
            )
        }
        composable(Destination.ServiceEditor.name) {
            val koin = remember { getKoin() }
            val hostViewModel = remember { koin.get<HostViewModel>() }
            val state by hostViewModel.state.collectAsState()
            HostEditorView(
                state = state,
                modifier = modifier,
                onCreateHost = hostViewModel::createHost
            )
        }
    }
}
