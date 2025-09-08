package org.bxkr.octodiary.domain.model.auth

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.data.model.auth.MosRuInfo
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.domain.model.DiaryId

@Serializable
sealed class AccessCredentials(
    val responsibleFor: DiaryId
) {
    @Serializable
    data class MesMosAccessCredentials(
        val accessToken: MesToken,
        val mosRuInfo: MosRuInfo?,
        val mosRefreshToken: String?
    ) : AccessCredentials(responsibleFor = DiaryId.MesMos)
}