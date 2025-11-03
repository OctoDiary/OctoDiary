package org.bxkr.octodiary.domain.model.student

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val studentId: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?
)