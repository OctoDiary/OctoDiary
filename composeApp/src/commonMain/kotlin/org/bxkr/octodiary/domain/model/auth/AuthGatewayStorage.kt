package org.bxkr.octodiary.domain.model.auth

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.data.model.auth.AuthEduInfo
import org.bxkr.octodiary.data.model.auth.MosRuInfo

@Serializable
sealed class AuthGatewayStorage {
    @Serializable
    data class MesMos(
        val mosRuInfo: MosRuInfo? = null
    ) : AuthGatewayStorage()

    @Serializable
    data class MesMosReg(
        val authEduInfo: AuthEduInfo? = null
    ) : AuthGatewayStorage()
}