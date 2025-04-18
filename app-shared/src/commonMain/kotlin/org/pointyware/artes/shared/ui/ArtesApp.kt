package org.pointyware.artes.shared.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pointyware.artes.navigation.Destination
import org.pointyware.artes.ui.Res
import org.pointyware.artes.ui.acc_desc_agents
import org.pointyware.artes.ui.acc_desc_services
import org.pointyware.artes.ui.agent_24
import org.pointyware.artes.ui.services_24

/**
 * The root for Compose UI.
 */
@Composable
fun ArtesApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val selectedItem = backStackEntry
        ?.destination
        ?.route
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            item(
                selected = selectedItem == Destination.AgentList.name,
                onClick = {
                    navController.navigate(Destination.AgentList.name)
                },
                icon = {
                    Icon(
                        painter = painterResource(Res.drawable.agent_24),
                        contentDescription = stringResource(Res.string.acc_desc_agents)
                    )
                }
            )
            item(
                selected = selectedItem == Destination.ServiceList.name,
                onClick = {
                    navController.navigate(Destination.ServiceList.name)
                },
                icon = {
                    Icon(
                        painter = painterResource(Res.drawable.services_24),
                        contentDescription = stringResource(Res.string.acc_desc_services)
                    )
                }
            )
        }
    ) {
        AgentServiceNavigation(
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )
    }
}
