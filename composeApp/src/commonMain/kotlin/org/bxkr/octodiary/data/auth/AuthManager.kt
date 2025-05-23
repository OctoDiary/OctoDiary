package org.bxkr.octodiary.data.auth

import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.bxkr.octodiary.Region
import org.bxkr.octodiary.data.RegionInfo
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.auth.token.MesToken
import org.bxkr.octodiary.exception.DeadTokenException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AuthManager {
    val isAuthorized: Flow<Boolean>
    suspend fun getAccessToken(): MesToken
    suspend fun updateAccessToken(newToken: String)
    suspend fun logOut(reason: String)
    suspend fun authorize(token: String, region: Region)
    suspend fun getRegion(): Region
}

class AuthManagerImpl : AuthManager, KoinComponent {
    private val kStore: KStore<StorageLatest> by inject()
    override val isAuthorized: Flow<Boolean> = kStore.updates.map { it?.accessToken != null }

    override suspend fun getAccessToken(): MesToken =
        kStore.get()?.accessToken?.let { MesToken(it) }
            .also { if (it?.isAlive() == false) throw DeadTokenException() }
            ?: throw IllegalStateException(
                "No authorization found"
            )

    override suspend fun updateAccessToken(newToken: String) =
        kStore.update { it?.copy(accessToken = newToken) }

    override suspend fun logOut(reason: String) {
        kStore.update { it?.copy(accessToken = null, regionInfo = null) }
    }

    override suspend fun authorize(token: String, region: Region) {
        kStore.update { it?.copy(accessToken = token, regionInfo = RegionInfo(region)) }
    }

    override suspend fun getRegion(): Region =
        kStore.get()?.regionInfo?.region ?: throw IllegalStateException("Not authorization found")
}