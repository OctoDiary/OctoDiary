package org.bxkr.octodiary.domain.model.ranking

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Ranking(
    val members: List<RankingMember>,
    val type: RankingType,
    val updatedAt: LocalDateTime? = null
)
