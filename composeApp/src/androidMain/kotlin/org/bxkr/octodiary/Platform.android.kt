package org.bxkr.octodiary

import android.content.ClipData
import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import org.koin.core.scope.Scope

class AndroidPlatformImpl(context: Context) : AndroidPlatform {
    override val name: String = "Android ${Build.VERSION.SDK_INT} - context $context"
}

actual fun getPlatform(scope: Scope): Platform = AndroidPlatformImpl(scope.get())

@Composable
actual fun getDynamicColorScheme(isDark: Boolean): ColorScheme? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else null

actual fun String.asClipEntry(): ClipEntry = ClipData.newPlainText("Copied", this).toClipEntry()
actual fun ClipEntry.getText(): String? = clipData.getItemAt(0).text.toString()