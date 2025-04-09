package org.pointyware.artes.shared.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.mp.KoinPlatform.getKoin
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class KoinSharedModuleIntegrationTest {

    private lateinit var moduleUnderTest: Module

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                sharedModule()
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun checkModules() {
        getKoin().checkModules()
    }
}
