package org.bxkr.octodiary.domain.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.DiaryId
import org.bxkr.octodiary.domain.model.error.DomainError

sealed class AuthState {
    data object NotAuthorized : AuthState()

    @Serializable
    sealed class Callback : AuthState() {
        @Serializable
        data class WaitingForCallback(
            val authMethod: AuthMethod,
            val diaryId: DiaryId
        ) : Callback()

        @Serializable
        data class HandlingCallback(
            val callbackState: CallbackState
        ) : Callback()

        @Serializable
        data class FailedToHandle(
            val authInfo: String,
            val exception: DomainError
        ) : Callback()
    }

    data object Authorized : AuthState()
}