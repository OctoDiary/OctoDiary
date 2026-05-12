package org.bxkr.octodiary

import android.app.Application
import org.bxkr.octodiary.di.KoinApp
import org.koin.android.ext.koin.androidContext
import org.koin.plugin.module.dsl.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin<KoinApp> {
            androidContext(this@MainApplication)
        }
    }
}