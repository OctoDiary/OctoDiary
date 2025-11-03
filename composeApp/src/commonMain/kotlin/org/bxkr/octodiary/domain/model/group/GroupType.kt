package org.bxkr.octodiary.domain.model.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GroupType {
    @SerialName("class")
    Class,

    @SerialName("group")
    Group,

    @SerialName("additional")
    Additional
}