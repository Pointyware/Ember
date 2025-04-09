package org.pointyware.artes.shared.di

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.test.verify.verify
import org.pointyware.artes.services.openai.network.OpenAiCredentials
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(KoinExperimentalAPI::class)
class KoinDesktopSharedModuleIntegrationTest {

    private lateinit var moduleUnderTest: Module

    @BeforeTest
    fun setUp() {
        moduleUnderTest = sharedModule().apply {
            single<OpenAiCredentials> {
                error("Do Not Use")
            }
        }
    }

    @AfterTest
    fun tearDown() {
    }

    @Test
    fun verify() {
        moduleUnderTest.verify(
            extraTypes = listOf(
                /*
                io.ktor.client.HttpClient is a class, so Koin tries to verify its creation by
                  checking for the constructor parameters, which are provided behind the scenes of
                  our kotlin DSL.
                 */
                HttpClientEngine::class,
                HttpClientConfig::class
            )
        )
    }
}
