package org.bxkr.octodiary.data.model.auth.accesscredentials.token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Payload {
    abstract val expiryDate: Long
    abstract val issuedAt: Long
}

@Serializable
data class MesPayload(
    @SerialName("msh")
    val personId: String,
    @SerialName("iss")
    val issuer: String,
    @SerialName("rgn")
    val region: String,
    @SerialName("exp")
    override val expiryDate: Long,
    @SerialName("iat")
    override val issuedAt: Long
) : Payload()

@Serializable
data class UchebnikPayload(
    @SerialName("exp")
    override val expiryDate: Long,
    @SerialName("iat")
    override val issuedAt: Long,
    @SerialName("msh")
    val mesPersonId: String,
    @SerialName("prf")
    val profiles: List<Prf>,
    @SerialName("rgn")
    val region: String,
    @SerialName("sso")
    val sso: String,
    @SerialName("stf")
    val stf: String,
    @SerialName("sub")
    val sub: String,
    @SerialName("usr")
    val uchebnikUserId: String
): Payload() {
    @Serializable
    data class Prf(
        @SerialName("id")
        val id: String,
        @SerialName("type")
        val type: String
    )
}