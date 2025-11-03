package org.bxkr.octodiary.domain.model.ranking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class RankingType {
    @Serializable
    @SerialName("common")
    data object Common : RankingType()

    @Serializable
    @SerialName("by-subject")
    data class BySubject(
        val subjectId: String
    ) : RankingType()
}