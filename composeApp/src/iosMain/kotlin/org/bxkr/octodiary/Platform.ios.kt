package org.bxkr.octodiary

import org.koin.core.scope.Scope
import platform.UIKit.UIDevice

class IOSPlatformImpl : IOSPlatform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(scope: Scope): Platform = IOSPlatformImpl()