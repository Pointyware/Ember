package org.pointyware.ember.shared.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformSharedModule(): Module {
    return module {
        single<CoroutineScope>(qualifier = appQualifier) {
            CoroutineScope(SupervisorJob() + Dispatchers.Main)
        }
    }
}
