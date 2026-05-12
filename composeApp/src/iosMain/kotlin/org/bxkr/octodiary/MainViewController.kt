package org.bxkr.octodiary

import androidx.compose.ui.window.ComposeUIViewController
import org.bxkr.octodiary.di.KoinApp
import org.bxkr.octodiary.ui.screen.App
import org.koin.plugin.module.dsl.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin<KoinApp> {}
    App()
}