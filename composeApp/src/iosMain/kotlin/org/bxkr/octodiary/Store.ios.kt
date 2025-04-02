package org.bxkr.octodiary

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.io.files.Path
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun getPaths(): Paths {
    val fileManager: NSFileManager = NSFileManager.defaultManager
    val documentsUrl = fileManager.URLForDirectory(
        directory = NSDocumentDirectory,
        appropriateForURL = null,
        create = false,
        inDomain = NSUserDomainMask,
        error = null
    )?.path

    val cachesUrl = fileManager.URLForDirectory(
        directory = NSCachesDirectory,
        appropriateForURL = null,
        create = false,
        inDomain = NSUserDomainMask,
        error = null
    )?.path

    if (documentsUrl == null || cachesUrl == null)
        throw IllegalStateException("Cannot access Apple directories!")

    return Paths(
        files = Path(documentsUrl),
        cache = Path(cachesUrl)
    )
}