package org.pointyware.artes.data

/**
 *
 */
interface PersistentDataSource {
    suspend fun get(key: String): String?
    suspend fun set(key: String, value: String)
    suspend fun remove(key: String)

    /**
     * Manually persist the data source.
     */
    suspend fun persist()
}
