package org.bxkr.octodiary.di

import org.bxkr.octodiary.DeeplinkHolder
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.mp.KoinPlatform

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    printLogger()
    includes(config)
    modules(appModule)
}

fun getDeeplinkHolder(): DeeplinkHolder = KoinPlatform.getKoin().get()