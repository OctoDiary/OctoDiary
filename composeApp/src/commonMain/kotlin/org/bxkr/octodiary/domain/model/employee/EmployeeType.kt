package org.bxkr.octodiary.domain.model.employee

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EmployeeType {
    @SerialName("teacher")
    Teacher,

    @SerialName("administrator")
    Administrator,

    @SerialName("headmaster")
    Headmaster,

    @SerialName("non_educational")
    NonEducational
}