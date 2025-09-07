package org.bxkr.octodiary.di

import org.bxkr.octodiary.di.AppModule
import org.bxkr.octodiary.di.DeeplinkHolder
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.mp.KoinPlatform
import org.koin.ksp.generated.*

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    printLogger()
    includes(config)
    modules(AppModule().module)
}

fun getDeeplinkHolder(): DeeplinkHolder = KoinPlatform.getKoin().get()