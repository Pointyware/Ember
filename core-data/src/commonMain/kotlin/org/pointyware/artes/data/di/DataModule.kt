package org.pointyware.artes.data.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataQualifier = named("data")

/**
 */
fun dataModule() = module {
    single<CoroutineDispatcher>(qualifier = dataQualifier) {
        Dispatchers.IO
    }
    single<CoroutineScope>(qualifier = dataQualifier) {
        CoroutineScope(get<CoroutineDispatcher>(qualifier = dataQualifier) + SupervisorJob())
    }
}
