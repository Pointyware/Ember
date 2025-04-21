package org.pointyware.artes.shared.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import org.jetbrains.compose.resources.stringResource
import org.pointyware.artes.agents.ui.AgentEditorScreen
import org.pointyware.artes.agents.ui.AgentListScreen
import org.pointyware.artes.agents.viewmodels.AgentEditorViewModel
import org.pointyware.artes.hosts.ui.HostEditorScreen
import org.pointyware.artes.navigation.Destination
import org.pointyware.artes.ui.AgentInfoView
import org.pointyware.artes.ui.HostConfigListView
import org.pointyware.artes.ui.Res
import org.pointyware.artes.ui.acc_desc_back
import org.pointyware.artes.ui.rememberViewModel
import org.pointyware.artes.ui.title_app
import org.pointyware.artes.viewmodels.DefaultAgentInfoViewModel
import org.pointyware.artes.viewmodels.DefaultAgentListViewModel
import org.pointyware.artes.viewmodels.DefaultServiceListViewModel


/**
 * Localizes navigation logic for the Agent and Service screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentServiceNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Column {
        val backStackEntry by navController.currentBackStackEntryAsState()
        TopAppBar(
            title = { Text(text = stringResource(Res.string.title_app)) },
            navigationIcon = {
                // Show back button if we are not at the root of the back stack
                if (backStackEntry != null) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.acc_desc_back)
                        )
                    }
                }
            }
        )
        NavHost(
            navController = navController,
            startDestination = Destination.AgentList,
            modifier = modifier,
            enterTransition = {
                fadeIn(animationSpec = tween(700))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(700))
            },
            popEnterTransition = {
                slideInVertically(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutVertically(animationSpec = tween(300))
            }
        ) {
            composable<Destination.AgentList> {
                val viewModel = rememberViewModel<DefaultAgentListViewModel>()
                AgentListScreen(
                    viewModel = viewModel,
                    onNewAgent = {
                        navController.navigate(Destination.AgentEditor(null))
                    },
                    onShowAgent = { agent ->
                        navController.navigate(Destination.AgentInfo(agent))
                    },
                )
            }
            composable<Destination.ServiceList> {
                Surface {
                    val viewModel = rememberViewModel<DefaultServiceListViewModel>()
                    val state by viewModel.state.collectAsState()
                    LaunchedEffect(Unit) {
                        viewModel.onInit()
                    }
                    HostConfigListView(
                        state = state,
                        modifier = modifier,
                        onRegisterService = {
                            navController.navigate(Destination.ServiceEditor(null))
                        }
                    )
                }
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
}
