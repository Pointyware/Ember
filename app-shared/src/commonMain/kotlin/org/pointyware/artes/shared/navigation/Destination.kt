package org.pointyware.artes.shared.navigation

/**
 * Describes navigation of the desktop application
 */
sealed interface Destination {
    object Workbench: Destination
    object Lab: Destination
    object Dashboard: Destination
    object Chat: Destination
}
