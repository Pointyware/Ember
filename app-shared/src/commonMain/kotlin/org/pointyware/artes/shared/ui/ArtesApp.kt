package org.pointyware.artes.shared.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.pointyware.artes.navigation.Destination

/**
 * The root for Compose UI.
 */
@Composable
fun ArtesApp() {

    Column {
        val navController = rememberNavController()
        Row {
            Button(
                onClick = {
                    navController.navigate(Destination.AgentList)
                }
            ) {
                Text("Agents")
            }
            Button(
                onClick = {
                    navController.navigate(Destination.ServiceList)
                }
            ) {
                Text("Services")
            }
        }
        AgentServiceNavigation(
            modifier = Modifier.weight(1f),
            navController = navController
        )
    }
}
