package org.pointyware.artes.hosts.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import org.pointyware.artes.hosts.viewmodels.ExtraOptionsUiState
import org.pointyware.artes.hosts.viewmodels.HostConfigUiState
import org.pointyware.artes.viewmodels.LoadingUiState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class HostEditorViewUnitTest {

    private var enteredHostName: String? = null
    private var enteredOrgId: String? = null
    private var enteredApiKey: String? = null

    @BeforeTest
    fun setUp() {
        enteredHostName = null
        enteredOrgId = null
        enteredApiKey = null
    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun submitInputOnButtonPress() = runComposeUiTest {
        /*
        Given: A NewHostView
         */
        setContent {
            HostEditorView( // TODO: rescript tests after changes in behavior
                state = HostConfigUiState(
                    title = "",
                    extraOptions = ExtraOptionsUiState.OpenAi(
                        orgId = "",
                        apiKey = "",
                    ),
                    loading = LoadingUiState.Idle
                ),
                onCreateHost = { title, extras ->
                    enteredHostName = title
                    extras as ExtraOptionsUiState.OpenAi
                    enteredOrgId = extras.orgId
                    enteredApiKey = extras.apiKey
                }
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

        /*
        Then: The onHostCreated callback should be called with the entered values
         */
        assertEquals("hostName", enteredHostName)
        assertEquals("orgId", enteredOrgId)
        assertEquals("apiKey", enteredApiKey)
    }
}
