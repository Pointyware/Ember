package org.pointyware.artes.viewmodels.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.pointyware.artes.viewmodels.AgentListViewModel
import org.pointyware.artes.viewmodels.DefaultAgentListViewModel

/**
 *
 */
fun viewModelModule() = module {
    factoryOf(::DefaultAgentListViewModel) {
        bind<AgentListViewModel>()
    }
}
