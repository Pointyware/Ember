package org.pointyware.artes.data.di

import org.koin.dsl.module
import org.pointyware.artes.data.AndroidDriverFactory
import org.pointyware.artes.local.DriverFactory

actual fun platformDataModule() = module {
    single<DriverFactory> {
        AndroidDriverFactory(get())
    }
}
