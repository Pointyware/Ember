package org.pointyware.artes.services.openai.network

import io.ktor.client.HttpClient
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import kotlin.test.Test

val completionPrompt = """
        ### User: This is part of a unit test suite. Give a short response.
    """.trimIndent()
/**
 */
class OpenAiTextCompletionServiceTest {


    @Before
    fun setUp() {
        given = Given(this)
        `when` = When(given)
        then = Then(`when`)
    }

    @After
    fun tearDown() {
    }

    class Given(val test: OpenAiTextCompletionServiceTest) {
        operator fun invoke(conditions: Given.()->Unit) {
            conditions()
        }

        lateinit var okHttp: HttpClient
        fun `an okhttp client configured for openAi`() {
            okHttp = openAiHttpClient()
        }

        lateinit var credentials: OpenAiCredentials
        fun `valid OpenAi credentials`() {
            credentials = openAiCredentials().getOrThrow()
        }
        fun `invalid OpenAi credentials`() {
            val validCredentials = openAiCredentials().getOrThrow()
            credentials = validCredentials.copy(apiKey = "InvalidKey")
        }

        lateinit var openAiTextCompletionService: OpenAiTextCompletionService
        fun `the service is constructed`() {
            openAiTextCompletionService = OpenAiTextCompletionService(okHttp, credentials)
        }
    }
    lateinit var given: Given
    class When(val given: Given) {

        suspend operator fun invoke(actions: suspend When.()->Unit) {
            actions()
        }

        var result: Result<String> = Result.failure(IllegalStateException("Unitialized"))
        suspend fun `a completion request is sent`() {
            result = runCatching { given.openAiTextCompletionService.complete(completionPrompt) }
        }
    }
    lateinit var `when`: When
    class Then(val `when`: When) {
        val given: Given get() = `when`.given
        operator fun invoke(assertions: Then.()->Unit) {
            assertions()
        }

        fun `a completion response is received`() {
            with(`when`.result) {
                assertTrue("$this", isSuccess)
            }
        }

        fun `an error response is received`() {
            with(`when`.result) {
                assertTrue("$this", isFailure)
            }
        }
    }
    lateinit var then: Then

    @Test
    fun `test text completion with valid credentials`() = runBlocking {
        given {
            `an okhttp client configured for openAi`()
            `valid OpenAi credentials`()
            `the service is constructed`()
        }

        `when` {
            `a completion request is sent`()
        }

        then {
            `a completion response is received`()
        }
    }

    @Test
    fun `text completion with invalid credentials`() = runBlocking {
        given {
            `an okhttp client configured for openAi`()
            `invalid OpenAi credentials`()
            `the service is constructed`()
        }

        `when` {
            `a completion request is sent`()
        }

        then {
            `an error response is received`()
        }
    }
}
