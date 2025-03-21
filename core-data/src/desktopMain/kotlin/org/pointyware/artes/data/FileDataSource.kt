package org.pointyware.artes.data

import java.io.File

/**
 *
 */
class FileDataSource(
    private val data: File
): PersistentDataSource {

    private val separator = "\n"
    private val assignment = "="
    private val map = mutableMapOf<String, String>()

    init {
        if (data.exists()) {
            val text = data.readText()
            val entries = text
                .split(separator)
                .mapNotNull { entry -> entry.split(assignment).takeIf { it.size == 2 } }
                .associate { it[0] to it[1] }
            map.putAll(entries)
        } else {
            data.createNewFile()
        }
    }

    override suspend fun get(key: String): String? {
        return map[key]
    }

    override suspend fun set(key: String, value: String) {
        require(key.contains(separator).not()) {
            val separatorHex = separator.encodeToByteArray().joinToString("") { "%02x".format(it) }
            val assignmentHex = assignment.encodeToByteArray().joinToString("") { "%02x".format(it) }
            "Key cannot contain the separator(0x${separatorHex}) or " +
                "the assignment(0x${assignmentHex}) character."
        }
        map[key] = value
    }

    override suspend fun remove(key: String) {
        map.remove(key)
    }

    override suspend fun persist() {
        data.writeText(map.entries.joinToString(separator = separator) { "${it.key}$assignment${it.value}" })
    }
}
