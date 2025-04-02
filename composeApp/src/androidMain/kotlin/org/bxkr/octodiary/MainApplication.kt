package org.bxkr.octodiary

import android.app.Application
import kotlinx.io.files.Path
import org.bxkr.octodiary.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val paths = applicationContext.run {
            Paths(
                Path(filesDir.path),
                Path(cacheDir.path)
            )
        }

        val androidModule = module {
            single { paths }
        }

        initKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(androidModule)
        }
    }
}