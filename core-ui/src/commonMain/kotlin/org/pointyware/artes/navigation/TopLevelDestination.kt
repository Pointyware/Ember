package org.pointyware.artes.navigation

import androidx.navigation.NavController
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.pointyware.artes.ui.Res
import org.pointyware.artes.ui.acc_desc_agents
import org.pointyware.artes.ui.acc_desc_services
import org.pointyware.artes.ui.agent_24
import org.pointyware.artes.ui.services_24

/**
 */
data class TopLevelDestination(
    val destination: Destination,
    val labelRes: StringResource,
    val iconRes: DrawableResource,
    val contentDescriptionRes: StringResource
) {
    companion object {
        val AgentList = TopLevelDestination(
            destination = Destination.AgentList,
            labelRes = Res.string.acc_desc_agents,
            iconRes = Res.drawable.agent_24,
            contentDescriptionRes = Res.string.acc_desc_agents
        )
        val ServiceList = TopLevelDestination(
            destination = Destination.ServiceList,
            labelRes = Res.string.acc_desc_services,
            iconRes = Res.drawable.services_24,
            contentDescriptionRes = Res.string.acc_desc_services
        )
        val All = listOf(
            AgentList,
            ServiceList
        )
    }
}

fun NavController.navigateTo(
    destination: TopLevelDestination,
) {
    this.navigate(destination.destination.name) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
