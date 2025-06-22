package org.pointyware.artes

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.pointyware.artes.shared.di.sharedModule

/**
 * Ember-specific implementation of the Android Application class.
 *
 * Start-up tasks:
 * - Initialize Koin with application context
 */
class EmberApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@EmberApplication)
            modules(sharedModule())
        }
    }
}
