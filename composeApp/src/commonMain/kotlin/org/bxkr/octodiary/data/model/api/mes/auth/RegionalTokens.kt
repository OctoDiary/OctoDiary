package org.bxkr.octodiary.data.model.api.mes.auth

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken

@Serializable
data class RegionalTokens(
    val accessToken: MesToken,
    val refreshToken: String
)
