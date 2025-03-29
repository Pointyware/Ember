package org.pointyware.artes.local

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

/**
 * Extension property to get and set the version of the database.
 */
var SqlDriver.version: Long
    get() = executeQuery(
        identifier = null,
        sql = "PRAGMA user_version;",
        mapper = { query ->
            query.next() // Android Driver does not prime the cursor
            QueryResult.Value(query.getLong(0) ?: 0L)
        },
        parameters = 0,
        binders = null,
    ).value
    set(value) {
        execute(
            identifier = null,
            sql = "PRAGMA user_version = $value;",
            parameters = 0,
            binders = null
        )
    }

/**
 * Extension function to create or migrate the database schema.
 */
fun <T : QueryResult<Unit>> SqlSchema<T>.createOrMigrate(driver: SqlDriver) {
    val lastVersion = driver.version
    val newVersion = version
    if (lastVersion == 0L) {
        create(driver)
        driver.version = newVersion
    } else {
        migrate(driver, lastVersion, newVersion)
    }
}
