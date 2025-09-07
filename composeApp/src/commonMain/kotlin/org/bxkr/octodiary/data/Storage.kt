package org.bxkr.octodiary.data

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.auth.AccessCredentials
import org.bxkr.octodiary.domain.model.auth.AuthGatewayStorage
import org.bxkr.octodiary.domain.model.auth.AuthMethod
import org.bxkr.octodiary.domain.model.auth.AuthState

typealias StorageLatest = StorageV1

const val storageVersion = 1

@Serializable
data class StorageV1(
    val accessCredentials: AccessCredentials? = null,
    val authGatewayStorage: AuthGatewayStorage? = null,
    val callbackAuthState: AuthState.Callback? = null
) {
    val currentDiaryId
        get() = accessCredentials?.responsibleFor

    val authState
        get() = if (accessCredentials != null)
            AuthState.Authorized
        else callbackAuthState ?: AuthState.NotAuthorized
}