package org.bxkr.octodiary.data.auth

import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.data.StorageLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object PreAuthManager : KoinComponent {
    private val kStore: KStore<StorageLatest> = get()

    fun isAuthorized(): Flow<Result<Boolean>> =
        kStore.updates.map { Result.Success(it?.accessToken != null) }

    suspend fun setAccessToken(newToken: String) =
        kStore.update { it?.copy(accessToken = newToken) }
}