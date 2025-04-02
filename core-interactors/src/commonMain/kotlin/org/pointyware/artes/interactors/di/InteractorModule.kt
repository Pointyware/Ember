package org.pointyware.artes.interactors.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.pointyware.artes.interactors.agents.GetAgentUseCase
import org.pointyware.artes.interactors.hosts.GetHostServicesUseCase

/**
 *
 */
fun interactorsModule() = module {
    factoryOf(::GetHostServicesUseCase)
    factoryOf(::GetAgentUseCase)
}
