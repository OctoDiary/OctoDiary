package org.bxkr.octodiary.domain.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CallbackState {
    @SerialName("initialized")
    data object Initialized : CallbackState()
    @SerialName("loading")
    data object Loading : CallbackState()
}