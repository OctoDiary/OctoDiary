package org.bxkr.octodiary

import org.koin.core.scope.Scope

interface Platform {
    val name: String
}

interface IOSPlatform : Platform
interface AndroidPlatform : Platform

expect fun getPlatform(scope: Scope): Platform