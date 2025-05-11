package org.bxkr.octodiary

import android.content.Context
import android.os.Build
import org.koin.core.scope.Scope

class AndroidPlatformImpl(context: Context) : AndroidPlatform {
    override val name: String = "Android ${Build.VERSION.SDK_INT} - context $context"
}

actual fun getPlatform(scope: Scope): Platform = AndroidPlatformImpl(scope.get())