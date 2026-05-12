package org.bxkr.octodiary

import android.content.Context
import kotlinx.io.files.Path
import org.koin.core.scope.Scope

actual fun getPaths(scope: Scope): Paths {
    val context: Context = scope.get()
    return Paths(
        Path(context.filesDir.path),
        Path(context.cacheDir.path)
    )
}