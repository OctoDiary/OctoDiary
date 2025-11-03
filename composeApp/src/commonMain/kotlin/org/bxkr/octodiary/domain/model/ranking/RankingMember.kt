package org.bxkr.octodiary.domain.model.ranking

import kotlinx.serialization.Serializable

@Serializable
data class RankingMember(
    val place: Int,
    val studentId: String,
    val result: String,
    val fullName: String? = null
)
