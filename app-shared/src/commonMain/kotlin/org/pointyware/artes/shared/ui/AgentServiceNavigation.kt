package org.pointyware.artes.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.pointyware.artes.agents.ui.AgentEditorScreen
import org.pointyware.artes.agents.ui.AgentListScreen
import org.pointyware.artes.agents.viewmodels.AgentEditorViewModel
import org.pointyware.artes.hosts.ui.HostEditorScreen
import org.pointyware.artes.navigation.Destination
import org.pointyware.artes.ui.AgentInfoView
import org.pointyware.artes.ui.ServiceListView
import org.pointyware.artes.ui.rememberViewModel
import org.pointyware.artes.viewmodels.DefaultAgentInfoViewModel
import org.pointyware.artes.viewmodels.DefaultAgentListViewModel
import org.pointyware.artes.viewmodels.DefaultServiceListViewModel


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
        startDestination = Destination.AgentList,
        modifier = modifier
    ) {
        composable<Destination.AgentList> {
            val viewModel = rememberViewModel<DefaultAgentListViewModel>()
            AgentListScreen(
                viewModel = viewModel,
                onNewAgent = {
                    navController.navigate(Destination.AgentEditor)
                },
                onShowAgent = { agent ->
                    navController.navigate(Destination.AgentInfo(agent))
                },
            )
        }
        composable<Destination.AgentInfo> {
            val agentInfoViewModel = rememberViewModel<DefaultAgentInfoViewModel>()
            val state by agentInfoViewModel.state.collectAsState()
            AgentInfoView(
                state = state.let {
                    TODO("Replace ViewModel.UiState with UiState")
                },
                modifier = modifier,
                onDelete = agentInfoViewModel::onDelete,
                onEdit = agentInfoViewModel::onEdit
            )
        }
        composable<Destination.AgentEditor> {
            AgentEditorScreen(
                viewModel = rememberViewModel<AgentEditorViewModel>(),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<Destination.ServiceList> {
            val viewModel = rememberViewModel<DefaultServiceListViewModel>()
            val state by viewModel.state.collectAsState()
            LaunchedEffect(Unit) {
                viewModel.onInit()
            }
            ServiceListView(
                state = state,
                modifier = modifier,
                onRegisterService = {
                    navController.navigate(Destination.ServiceEditor)
                }
            )
        }
        composable<Destination.ServiceEditor> {
            HostEditorScreen(
                viewModel = rememberViewModel(),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
