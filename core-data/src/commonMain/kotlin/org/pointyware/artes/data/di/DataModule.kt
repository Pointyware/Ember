package org.pointyware.artes.data.di

import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.artes.data.agents.AgentRepository
import org.pointyware.artes.data.agents.AgentRepositoryImpl
import org.pointyware.artes.data.hosts.db.HostDb
import org.pointyware.artes.local.DriverFactory
import org.pointyware.artes.local.Persistence
import org.pointyware.artes.local.createOrMigrate

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

    single<AgentRepository> {
        AgentRepositoryImpl(
            get(),
            get()
        )
    }

    single<Lazy<HostDb>> { lazy {
        val driver: SqlDriver = get()
        HostDb.Schema.createOrMigrate(driver)
        HostDb(driver)
    } }
    single<SqlDriver> {
        val persistence: Persistence? = getOrNull()
        val driverFactory = get<DriverFactory>()
        driverFactory.createSqlDriver(persistence ?: Persistence.File)
    }

    includes(
        platformDataModule()
    )
}

expect fun platformDataModule(): Module
