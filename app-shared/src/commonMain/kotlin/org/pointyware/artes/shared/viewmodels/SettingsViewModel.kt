package org.pointyware.artes.shared.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.artes.data.SettingsRepository
import org.pointyware.artes.services.openai.network.API_KEY
import org.pointyware.artes.services.openai.network.ORG_ID
import org.pointyware.artes.services.openai.network.OpenAiCredentials

/**
 *
 */
class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val coroutineScope: CoroutineScope
) {
    private val empty = SettingsUiState()

    private val mutableState = MutableStateFlow(empty)
    val state: StateFlow<SettingsUiState> = mutableState

    init {
        coroutineScope.launch {
            launch {
                settingsRepository.onValueChange.collect { key ->
                    when (key) {
                        ORG_ID -> {
                            setOpenAiOrganization(
                                settingsRepository.get(ORG_ID) ?: ""
                            )
                        }

                        API_KEY -> {
                            settingsRepository.get(API_KEY)?.let { value ->
                                setOpenAiKey(value)
                            } ?: run {
                                clearOpenAiCredentials()
                            }
                        }
                    }
                }
            }
            launch {
                settingsRepository.get(API_KEY)?.let { key ->
                    setOpenAiKey(key)
                    settingsRepository.get(ORG_ID)?.let { org ->
                        setOpenAiOrganization(org)
                    }
                } ?: run {
                    clearOpenAiCredentials()
                }
            }
        }
    }

    fun setOpenAiOrganization(orgId: String) {
        mutableState.update { it.copy(
            openAiCredentials = it.openAiCredentials?.copy(
                orgId = orgId
            )
        ) }
    }

    fun setOpenAiKey(apiKey: String) {
        mutableState.update { it.copy(
            openAiCredentials = it.openAiCredentials?.copy(
                apiKey = apiKey
            ) ?: OpenAiCredentials(apiKey = apiKey),
            saveEnabled = apiKey.isNotBlank()
        ) }
    }

    fun clearOpenAiCredentials() {
        mutableState.update { it.copy(openAiCredentials = null) }
    }

    fun saveCredentials() {
        state.value.openAiCredentials?.let {
            coroutineScope.launch {
                mutableState.update { it.copy(saveSuccess = false) }
                settingsRepository.set(API_KEY, it.apiKey)
                it.orgId?.let { orgId ->
                    settingsRepository.set(ORG_ID, orgId)
                } ?: run {
                    settingsRepository.remove(ORG_ID)
                }
                settingsRepository.persist()
                mutableState.update { it.copy(saveSuccess = true) }
            }
        }
    }
}
