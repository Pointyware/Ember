package org.pointyware.artes.navigation

import androidx.navigation.NavController
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.pointyware.artes.ui.Res
import org.pointyware.artes.ui.acc_desc_agents
import org.pointyware.artes.ui.acc_desc_services
import org.pointyware.artes.ui.agent_24
import org.pointyware.artes.ui.label_agents
import org.pointyware.artes.ui.label_services
import org.pointyware.artes.ui.services_24

/**
 */
enum class TopLevelDestination(
    val destination: Destination,
    val labelRes: StringResource,
    val iconRes: DrawableResource,
    val contentDescriptionRes: StringResource
) {
    AgentList(
        destination = Destination.AgentList,
        labelRes = Res.string.label_agents,
        iconRes = Res.drawable.agent_24,
        contentDescriptionRes = Res.string.acc_desc_agents
    ),
    ServiceList(
        destination = Destination.ServiceList,
        labelRes = Res.string.label_services,
        iconRes = Res.drawable.services_24,
        contentDescriptionRes = Res.string.acc_desc_services
    )
}

fun NavController.navigateTo(
    destination: TopLevelDestination,
) {
    this.navigate(destination.destination) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
