package org.bxkr.octodiary

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipEntry
import org.koin.core.scope.Scope

interface Platform {
    val name: String
}

interface IOSPlatform : Platform
interface AndroidPlatform : Platform

expect fun getPlatform(scope: Scope): Platform

@Composable
expect fun getDynamicColorScheme(isDark: Boolean): ColorScheme?

expect fun String.asClipEntry(): ClipEntry

expect fun ClipEntry.getText(): String?