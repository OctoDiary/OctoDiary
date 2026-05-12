package org.bxkr.octodiary

import kotlinx.io.files.Path
import org.koin.core.scope.Scope

data class Paths(
    val files: Path,
    val cache: Path
)

expect fun getPaths(scope: Scope): Paths