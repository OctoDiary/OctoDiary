package org.bxkr.octodiary.data.auth

import io.github.xxfast.kstore.KStore
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.auth.token.MesToken
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AuthManager {
    suspend fun getAccessToken(): MesToken
    suspend fun updateAccessToken(newToken: String)
}

class AuthManagerImpl : AuthManager, KoinComponent {
    private val kStore: KStore<StorageLatest> by inject()

    override suspend fun getAccessToken(): MesToken =
        kStore.get()?.accessToken?.let { MesToken(it) } ?: throw IllegalStateException(
            "No authorization found. Please do not instantiate AuthManager"
        )

    override suspend fun updateAccessToken(newToken: String) =
        kStore.update { it?.copy(accessToken = newToken) }
}