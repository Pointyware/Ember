package org.pointyware.artes.navigation

import androidx.navigation.NavController
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.pointyware.ember.ui.Res
import org.pointyware.ember.ui.agent_24
import org.pointyware.ember.ui.title_destination_lab
import org.pointyware.ember.ui.acc_desc_lab

/**
 * Defines the top-level destinations in the app that will be shown in the bottom navigation bar.
 *
 * @param destination The application-level destination that this top-level destination represents.
 */
enum class TopLevelDestination(
    val destination: Destination,
    val labelRes: StringResource,
    val iconRes: DrawableResource,
    val contentDescriptionRes: StringResource
) {
    Lab(
        destination = Destination.Lab("TODO: replace me with sensible TLD"),
        labelRes = Res.string.title_destination_lab,
        iconRes = Res.drawable.agent_24,
        contentDescriptionRes = Res.string.acc_desc_lab
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
