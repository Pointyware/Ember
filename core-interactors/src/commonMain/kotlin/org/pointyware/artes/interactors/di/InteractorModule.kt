package org.pointyware.artes.interactors.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.pointyware.artes.interactors.BeginAgentCreationJourney
import org.pointyware.artes.interactors.GetAgentListUseCase
import org.pointyware.artes.interactors.agents.GetAgentUseCase
import org.pointyware.artes.interactors.hosts.GetHostServicesUseCase

/**
 *
 */
fun interactorsModule() = module {
    factoryOf(::GetHostServicesUseCase)
    factoryOf(::GetAgentUseCase)
    factoryOf(::GetAgentListUseCase)
    factoryOf(::BeginAgentCreationJourney)
}
