package org.pointyware.artes.data

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FileDataSourceTest {

    @Before
    fun setUp() {
        given = Given()
        `when` = When(given)
        then = Then(`when`)
    }

    @After
    fun tearDown() {
        given.data.deleteOnExit()
    }

    class Given {
        lateinit var data: File
        lateinit var dataSource: FileDataSource
        fun `an empty file`() {
            data = File.createTempFile("test", "txt")
            dataSource = FileDataSource(data)
        }

        fun `a file containing pairs`(listOf: List<Pair<String, String>>) {
            data = File.createTempFile("test", "txt")
            data.writeText(listOf.joinToString(separator = "\n") { "${it.first}=${it.second}" })
            dataSource = FileDataSource(data)
        }
    }
    lateinit var given: Given

    class When(val given: Given) {
        var result: String? = null
        suspend fun `get is called with key`(key: String) {
            result = given.dataSource.get(key)
        }

        suspend fun `remove is called with key`(key: String) {
            given.dataSource.remove(key)
        }

        suspend fun `data source is persisted`() {
            given.dataSource.persist()
        }

        suspend fun `set is called with key and value`(key: String, value: String) {
            given.dataSource.set(key, value)
        }
    }
    lateinit var `when`: When

    class Then(private val `when`: When) {
        fun `the result is null`() {
            assertThat(`when`.result).isNull()
        }

        fun `the result is`(expected: String) {
            assertThat(`when`.result).isEqualTo(expected)
        }

        suspend fun `the data source returns null for key`(key: String) {
            assertThat(given.dataSource.get(key)).isNull()
        }

        fun `the file contains`(expected: Map<String, String>) {
            val text = given.data.readText()
            val entries = text
                .split("\n")
                .map { it.split("=") }
                .associate { it[0] to it[1] }
            assertThat(entries).containsExactlyEntriesIn(expected)
        }

        suspend fun `the data source returns value for key`(key: String, value: String) {
            assertThat(given.dataSource.get(key)).isEqualTo(value)
        }

        val given = `when`.given
    }
    lateinit var then: Then

    @Test
    fun `data source returns null for all keys with empty file`() = runTest {
        given.`an empty file`()

        `when`.`get is called with key`("key")

        then.`the result is null`()
    }

    @Test
    fun `data source returns non-null values defined in file`() = runTest {
        given.`a file containing pairs`(listOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3",
        ))

        `when`.`get is called with key`("key1")
        then.`the result is`("value1")
    }

    @Test
    fun `set overwrites existing values`() = runTest {
        given.`a file containing pairs`(listOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3",
        ))
        `when`.`set is called with key and value`("key2", "value2.1")
        then.`the data source returns value for key`("key2", "value2.1")
    }

    @Test
    fun `set adds news values`() = runTest {
        given.`a file containing pairs`(listOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3",
        ))
        `when`.`set is called with key and value`("key4", "value4")
        then.`the data source returns value for key`("key4", "value4")
    }

    @Test
    fun remove() = runTest {
        given.`a file containing pairs`(listOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3",
        ))
        `when`.`remove is called with key`("key2")
        then.`the data source returns null for key`("key2")
    }

    @Test
    fun persist() = runTest {
        given.`a file containing pairs`(listOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3",
        ))

        `when`.`remove is called with key`("key2")
        then.`the data source returns null for key`("key2")

        `when`.`data source is persisted`()
        then.`the file contains`(mapOf(
            "key1" to "value1",
            "key3" to "value3",
        ))
    }
}
