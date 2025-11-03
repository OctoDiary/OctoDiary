package org.bxkr.octodiary.domain.model.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OrganizationType {
    @SerialName("preschool")
    Preschool,

    @SerialName("school")
    School,

    @SerialName("college")
    College,

    @SerialName("university")
    University
}