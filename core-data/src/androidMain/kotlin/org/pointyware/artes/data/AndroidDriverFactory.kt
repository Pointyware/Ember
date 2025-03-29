package org.pointyware.artes.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.pointyware.artes.data.hosts.db.HostDb
import org.pointyware.artes.local.DriverFactory

/**
 *
 */
class AndroidDriverFactory(private val context: Context): DriverFactory {
    override fun createSqlDriver(path: String): SqlDriver {
        return if (path.isEmpty()) {
            AndroidSqliteDriver(HostDb.Schema, context)
        } else {
            AndroidSqliteDriver(HostDb.Schema, context, path)
        }
    }
}
