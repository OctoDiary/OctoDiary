package org.bxkr.octodiary

import android.content.Context
import android.os.Build
import org.koin.core.scope.Scope

class AndroidPlatform(context: Context) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT} - context $context"
}

actual fun getPlatform(scope: Scope): Platform = AndroidPlatform(scope.get())