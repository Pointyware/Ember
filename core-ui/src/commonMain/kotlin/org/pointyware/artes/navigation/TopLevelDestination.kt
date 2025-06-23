package org.pointyware.artes.navigation

import androidx.navigation.NavController
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.pointyware.ember.ui.Res
import org.pointyware.ember.ui.acc_desc_back
import org.pointyware.ember.ui.agent_24
import org.pointyware.ember.ui.title_app

/**
 * Defines the top-level destinations in the app that will be shown in the bottom navigation bar.
 */
enum class TopLevelDestination(
    val destination: Destination,
    val labelRes: StringResource,
    val iconRes: DrawableResource,
    val contentDescriptionRes: StringResource
) {
    Placeholder(
        destination = Destination.Placeholder,
        labelRes = Res.string.title_app,
        iconRes = Res.drawable.agent_24,
        contentDescriptionRes = Res.string.acc_desc_back
    )
}

/**
 * Extension function to navigate to a top-level destination.
 */
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
