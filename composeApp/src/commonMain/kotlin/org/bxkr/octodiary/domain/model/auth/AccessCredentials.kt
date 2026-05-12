package org.bxkr.octodiary.domain.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bxkr.octodiary.data.model.auth.MosRuInfo
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.domain.model.diary.DiaryId

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

    @Serializable
    data class MesMosRegAccessCredentials(
        val accessToken: MesToken,
        val authEduRefreshToken: String
    ) : AccessCredentials(responsibleFor = DiaryId.MesMosReg)

    @Serializable
    @SerialName("demo_access_credentials")
    data object DemoAccessCredentials : AccessCredentials(responsibleFor = DiaryId.Demo)
}