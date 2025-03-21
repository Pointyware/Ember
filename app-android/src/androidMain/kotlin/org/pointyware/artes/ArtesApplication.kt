package org.pointyware.artes

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.pointyware.artes.shared.di.sharedModule

/**
 */
class ArtesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ArtesApplication)
            modules(sharedModule())
        }
    }
}
