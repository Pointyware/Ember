package org.pointyware.artes.shared.viewmodels

import org.pointyware.artes.services.openai.network.OpenAiCredentials

/**
 *
 */
data class SettingsUiState(
    val openAiCredentials: OpenAiCredentials? = null,
    val saveEnabled: Boolean = false,
    val saveSuccess: Boolean? = null,
    val error: String? = null,
)
