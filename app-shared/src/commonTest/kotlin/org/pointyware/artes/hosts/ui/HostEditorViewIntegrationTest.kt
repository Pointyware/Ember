package org.pointyware.artes.hosts.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.artes.data.di.dataQualifier
import org.pointyware.artes.data.hosts.ServiceRepository
import org.pointyware.artes.hosts.viewmodels.ExtraOptionsUiState
import org.pointyware.artes.hosts.viewmodels.HostConfigEditorUiState
import org.pointyware.artes.hosts.viewmodels.HostViewModel
import org.pointyware.artes.local.Persistence
import org.pointyware.artes.services.openai.OpenAiConfig
import org.pointyware.artes.shared.di.appQualifier
import org.pointyware.artes.shared.di.sharedModule
import org.pointyware.artes.viewmodels.LoadingUiState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class HostEditorViewIntegrationTest {

    @BeforeTest
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(
                sharedModule(),
                module {
                    single<CoroutineDispatcher>(qualifier = dataQualifier) {
                        testDispatcher
                    }
                    single<CoroutineDispatcher>(qualifier = appQualifier) {
                        testDispatcher
                    }
                    single<Persistence> {
                        Persistence.InMemory
                    }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun create_host_on_submit() = runComposeUiTest {
        /*
        Given: A NewHostView, a NewHostViewModel, and a HostRepository
         */
        val koin = getKoin()
        val viewModel = koin.get<HostViewModel>()
        val repository = koin.get<ServiceRepository>()
        setContent {
            HostEditorView(
                state = HostConfigEditorUiState(
                    title = "",
                    extraOptions = ExtraOptionsUiState.OpenAi(
                        orgId = "",
                        apiKey = ""
                    ),
                    loading = LoadingUiState.Idle
                ),
                onCreateHost = viewModel::createHost
            )
        }

        /*
        When: The user enters a host name, organization id, and api key, and presses the "Create Host" button
         */
        onNode(hasText("Host Name"))
            .performTextInput("hostName")
        onNode(hasText("Organization Id"))
            .performTextInput("orgId")
        onNode(hasText("API Key"))
            .performTextInput("apiKey")
        onNode(hasText("Create Host"))
            .performClick()

        runTest {
            /*
            Then: The new host record exists in the repository
             */
            val hostList = repository.getHosts()
            assertEquals(1, hostList.size)
            val host = hostList.first()
            assertEquals(host.title, "hostName")
            assertIs<OpenAiConfig>(host)
            assertEquals(host.orgId, "orgId")
            assertEquals(host.apiKey, "apiKey")
        }
    }
}
