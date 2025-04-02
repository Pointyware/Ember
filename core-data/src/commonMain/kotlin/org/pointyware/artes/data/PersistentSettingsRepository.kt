package org.pointyware.artes.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 *
 */
class PersistentSettingsRepository(
    private val persistentDataSource: PersistentDataSource
): SettingsRepository {

    private val mutableOnValueChange = MutableSharedFlow<String>()
    override val onValueChange: Flow<String> = mutableOnValueChange

    override suspend fun get(key: String): String? {
        return persistentDataSource.get(key)
    }

    override suspend fun set(key: String, value: String) {
        persistentDataSource.set(key, value)
        mutableOnValueChange.emit(key)
    }

    override suspend fun remove(key: String) {
        persistentDataSource.remove(key)
        mutableOnValueChange.emit(key)
    }

    override suspend fun persist() {
        persistentDataSource.persist()
    }
}
