package org.pointyware.ember.navigation

import kotlinx.serialization.Serializable

/**
 * Core navigation destinations.
 *
 * These are preferably exposed in a core-navigation module so that each feature can declare
 * its own destinations without needing to know about others except dependent features.
 *
 * These destinations are application-level logic and are distinct from the [TopLevelDestination]
 * which is a UI-specific implementation of controlling navigation.
 */
@Serializable
sealed interface Destination {
    @Serializable
    data class Lab(
        val networkId: String,
    ): Destination
}
