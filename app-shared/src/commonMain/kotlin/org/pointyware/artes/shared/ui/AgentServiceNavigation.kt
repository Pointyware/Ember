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
import org.pointyware.artes.ui.AgentInfoView
import org.pointyware.artes.ui.AgentInfoViewState
import org.pointyware.artes.ui.ServiceListItemState
import org.pointyware.artes.ui.ServiceListView
import org.pointyware.artes.ui.ServiceListViewState
import org.pointyware.artes.viewmodels.AgentInfoViewModel
import org.pointyware.artes.viewmodels.AgentListViewModel
import org.pointyware.artes.viewmodels.ServiceListViewModel

enum class Destination {
    AgentList,
    AgentInfo,
    AgentEditor,
    ServiceList
}


/**
 *
 */
@Composable
fun AgentServiceNavigation(
    agentInfoViewModel: AgentInfoViewModel,
    agentEditorViewModel: AgentEditorViewModel,
    serviceListViewModel: ServiceListViewModel,
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
            AgentInfoView(
                state = AgentInfoViewState(
                    name = "Sam",
                    description = "A helpful agent",
                    service = ServiceListItemState(
                        id = 0L,
                        label = "Dev",
                        service = "OpenAI"
                    )
                ),
                modifier = modifier,
                onDelete = agentInfoViewModel::onDelete,
                onEdit = agentInfoViewModel::onEdit
            )
        }
        Destination.AgentEditor -> {
            AgentEditorView(
                state = agentEditorViewModel.state.collectAsState().value,
                modifier = modifier,
                onSelectHost = agentEditorViewModel::onSelectHost,
                onSubmit = agentEditorViewModel::onSave
            )
        }
        Destination.ServiceList -> {
            ServiceListView(
                state = ServiceListViewState(
                    services = listOf()
                ),
                modifier = modifier,
                onRegisterService = serviceListViewModel::onRegisterService
            )
        }
    }
}
