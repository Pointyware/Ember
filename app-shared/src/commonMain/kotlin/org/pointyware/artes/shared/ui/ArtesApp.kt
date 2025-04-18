package org.pointyware.artes.shared.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pointyware.artes.navigation.TopLevelDestination
import org.pointyware.artes.navigation.navigateTo

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
            TopLevelDestination.All.forEach {
                item(
                    selected = selectedItem == it.destination.name,
                    onClick = {
                        navController.navigateTo(it)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(it.iconRes),
                            contentDescription = stringResource(it.contentDescriptionRes)
                        )
                    },
                    label = { Text(text = stringResource(it.labelRes)) }
                )
            }
        }
    ) {
        AgentServiceNavigation(
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )
    }
}
