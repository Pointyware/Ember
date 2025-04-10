package org.pointyware.artes.hosts.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class NewHostConfigViewUnitTest {

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
            NewHostView(
                onHostCreated = { hostName, orgId, apiKey ->
                    enteredHostName = hostName
                    enteredOrgId = orgId
                    enteredApiKey = apiKey
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
