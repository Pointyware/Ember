package org.pointyware.artes.shared.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pointyware.artes.navigation.Destination
import org.pointyware.artes.ui.Res
import org.pointyware.artes.ui.acc_desc_agents
import org.pointyware.artes.ui.acc_desc_services
import org.pointyware.artes.ui.agent_24
import org.pointyware.artes.ui.services_24
import org.pointyware.artes.ui.title_app

/**
 * The root for Compose UI.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtesApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text =
                        stringResource(Res.string.title_app)
                    )
                },
            )
        },
        bottomBar = {
            NavigationBar {
                IconButton(
                    onClick = {
                        navController.navigate(Destination.AgentList.name)
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.agent_24),
                        contentDescription = stringResource(Res.string.acc_desc_agents)
                    )
                }
                IconButton(
                    onClick = {
                        navController.navigate(Destination.ServiceList.name)
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.services_24),
                        contentDescription = stringResource(Res.string.acc_desc_services)
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        AgentServiceNavigation(
            modifier = Modifier.padding(scaffoldPadding),
            navController = navController
        )
    }
}
