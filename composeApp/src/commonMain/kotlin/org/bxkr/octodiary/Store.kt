package org.bxkr.octodiary

import kotlinx.io.files.Path

data class Paths(
    val files: Path,
    val cache: Path
)

expect fun getPaths(): Paths