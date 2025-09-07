package org.bxkr.octodiary

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import org.koin.core.scope.Scope
import platform.UIKit.UIDevice

class IOSPlatformImpl : IOSPlatform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(scope: Scope): Platform = IOSPlatformImpl()

@Composable
actual fun getDynamicColorScheme(isDark: Boolean): ColorScheme? = null

@OptIn(ExperimentalComposeUiApi::class)
actual fun String.asClipEntry() = ClipEntry.withPlainText(this)

@OptIn(ExperimentalComposeUiApi::class)
actual fun ClipEntry.getText(): String? = getPlainText()