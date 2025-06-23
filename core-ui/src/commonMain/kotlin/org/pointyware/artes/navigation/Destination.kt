package org.pointyware.artes.navigation

import kotlinx.serialization.Serializable

/**
 * Core navigation destinations.
 *
 * The last four described navigation of the desktop reference application. These will be merged.
 */
@Serializable
sealed interface Destination {
}
