package org.bxkr.octodiary.domain.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserType {
    @SerialName("student")
    Student,

    @SerialName("parent")
    Parent
}