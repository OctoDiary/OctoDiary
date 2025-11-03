package org.bxkr.octodiary.di

import org.bxkr.octodiary.di.module.AppModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.ksp.generated.module
import org.koin.mp.KoinPlatform

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    printLogger()
    includes(config)
    modules(AppModule().module)
}

fun getDeeplinkHolder(): DeeplinkHolder = KoinPlatform.getKoin().get()