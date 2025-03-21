package org.pointyware.artes.data.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val dataQualifier = named("data")

/**
 */
fun dataModule() = module {
    single<CoroutineContext>(qualifier = dataQualifier) {
        Dispatchers.IO
    }
    single<CoroutineScope>(qualifier = dataQualifier) {
        CoroutineScope(get<CoroutineContext>(qualifier = dataQualifier) + SupervisorJob())
    }
}
