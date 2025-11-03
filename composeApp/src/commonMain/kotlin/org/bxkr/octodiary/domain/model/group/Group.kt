package org.bxkr.octodiary.domain.model.group

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val groupId: String,
    val fullName: String,
    val type: GroupType,
    val shortName: String? = null,
    val level: Int? = null,
    val members: List<GroupMember>? = null
)
