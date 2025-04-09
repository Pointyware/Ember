package org.pointyware.artes.shared.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.koin.test.check.checkModules
import org.pointyware.artes.services.openai.network.OpenAiCredentials
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
        unloadKoinModules(
            module {
                single<OpenAiCredentials> {
                    error("Do Not Use")
                }
            }
        )
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun checkModules() {
        getKoin().checkModules {
            withInstance(OpenAiCredentials("", ""))
        }
    }
}
