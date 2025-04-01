package org.bxkr.octodiary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform