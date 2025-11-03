package org.bxkr.octodiary.domain.model.mark

import kotlinx.serialization.Serializable

@Serializable
data class MarkGroupResult(
    val markValue: String,
    val studentCount: Int
)
