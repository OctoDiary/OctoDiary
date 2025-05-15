package org.bxkr.octodiary.network.apis

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import org.bxkr.octodiary.data.auth.AuthManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface DSchoolClient

class DSchoolClientImpl : DSchoolClient, KoinComponent {
    private val authManager: AuthManager by inject()
    private var _client: HttpClient? = null

    private suspend fun client(): HttpClient = _client ?: run {
        val region = authManager.getRegion()
        HttpClient {
            install(ContentNegotiation) { json(org.bxkr.octodiary.json) }
            defaultRequest { url(BaseUrl.getInstance(region).dSchoolApi) }
            install(HttpTimeout) {
                requestTimeoutMillis = 7000
                connectTimeoutMillis = 7000
                socketTimeoutMillis = 7000
            }
        }
    }
}