package org.bxkr.octodiary.domain.model.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupMember(
    val studentId: String,
    val name: String
)
