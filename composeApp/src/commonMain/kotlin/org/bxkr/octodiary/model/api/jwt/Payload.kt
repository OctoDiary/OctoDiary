package org.bxkr.octodiary.model.api.jwt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    @SerialName("exp")
    val expiryDate: Long,
    @SerialName("msh")
    val personId: String,
    @SerialName("iss")
    val issuer: String,
    @SerialName("rgn")
    val region: String
)