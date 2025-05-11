package org.bxkr.octodiary.model.internal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientCredentials(
    @SerialName("client_id") val clientId: String,
    @SerialName("client_secret") val clientSecret: String,
)