package org.pointyware.artes.data.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.artes.data.hosts.HostDao
import org.pointyware.artes.data.hosts.HostRepository
import org.pointyware.artes.data.hosts.HostRepositoryImpl
import org.pointyware.artes.data.hosts.SqlDelightHostDao

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

    single<HostRepository> {
        HostRepositoryImpl(
            hostDao = get(),
            ioDispatcher = get(qualifier = dataQualifier),
            ioScope = get(qualifier = dataQualifier)
        )
    }

    single<HostDao> {
        SqlDelightHostDao()
    }
}
