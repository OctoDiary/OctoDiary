package org.bxkr.octodiary

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PathsHolder : KoinComponent {
    val paths: Paths by inject()
}

actual fun getPaths(): Paths = PathsHolder().paths