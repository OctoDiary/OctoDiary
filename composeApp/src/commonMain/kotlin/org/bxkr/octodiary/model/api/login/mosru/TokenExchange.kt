package org.bxkr.octodiary.model.api.login.mosru

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenExchange(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("id_token")
    val idToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("scope")
    val scope: String,
    @SerialName("token_type")
    val tokenType: String,
) {
    @Serializable
    data class Refresh(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("expires_in")
        val expiresIn: Int,
        @SerialName("refresh_token")
        val refreshToken: String,
        @SerialName("scope")
        val scope: String,
        @SerialName("token_type")
        val tokenType: String,
    )
}
