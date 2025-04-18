package org.pointyware.artes.navigation

import kotlinx.serialization.Serializable

/**
 * Core navigation destinations.
 *
 * The last four described navigation of the desktop reference application. These will be merged.
 */
@Serializable
sealed interface Destination {
    @Serializable
    data object AgentList: Destination
    @Serializable
    data class AgentInfo(
        val id: Long
    ): Destination
    @Serializable
    data class AgentEditor(
        val id: Long? = null
    ): Destination
    @Serializable
    data object ServiceList: Destination
    @Serializable
    data class ServiceEditor(
        val id: Long? = null
    ): Destination

    @Serializable
    data object Workbench: Destination
    @Serializable
    data object Lab: Destination
    @Serializable
    data object Dashboard: Destination
    @Serializable
    data object Chat: Destination
}
