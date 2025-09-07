package org.bxkr.octodiary.data.model.auth.accesscredentials

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.data.model.auth.MosRuInfo
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.domain.model.DiaryId
import org.bxkr.octodiary.domain.model.auth.AccessCredentials
import org.bxkr.octodiary.domain.model.auth.AuthGatewayStorage

@Serializable
data class MesMosAccessCredentials(
    val accessToken: MesToken,
    val mosRuInfo: MosRuInfo?,
    val mosRefreshToken: String?
) : AccessCredentials(responsibleFor = DiaryId.MesMos)