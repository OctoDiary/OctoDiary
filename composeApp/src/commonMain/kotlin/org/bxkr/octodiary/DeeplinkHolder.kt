package org.bxkr.octodiary

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DeeplinkHolder {
    private val _deeplink: MutableStateFlow<String?> = MutableStateFlow(null)
    val deeplink = _deeplink.asStateFlow()

    fun updateDeeplink(url: String?) {
        _deeplink.value = url
    }
}