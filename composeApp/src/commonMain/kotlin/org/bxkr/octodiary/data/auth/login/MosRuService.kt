package org.bxkr.octodiary.data.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.UriHandler
import org.bxkr.octodiary.network.apis.MosAuthClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MosRuService : KoinComponent {
    private val mosAuthClient: MosAuthClient by inject()

    fun startAuth(uriHandler: UriHandler) {

    }

    @Composable
    fun AuthWebView() {

    }
}