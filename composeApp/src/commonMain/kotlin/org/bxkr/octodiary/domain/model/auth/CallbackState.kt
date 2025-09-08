package org.bxkr.octodiary.domain.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CallbackState {
    @Serializable
    @SerialName("initialized")
    data object Initialized : CallbackState()

    @Serializable
    @SerialName("loading")
    data object Loading : CallbackState()
}