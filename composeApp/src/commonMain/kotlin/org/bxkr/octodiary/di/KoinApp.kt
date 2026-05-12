package org.bxkr.octodiary.di

import org.bxkr.octodiary.di.module.AppModule
import org.koin.core.annotation.KoinApplication
import org.koin.mp.KoinPlatform

@KoinApplication(modules = [AppModule::class])
class KoinApp

fun getDeeplinkHolder(): DeeplinkHolder = KoinPlatform.getKoin().get()