package org.bxkr.octodiary.domain.model.user

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.student.Student

@Serializable
data class UserProfile(
    val primaryId: String,
    val secondaryId: String? = null,

    val firstName: String,
    val lastName: String,
    val middleName: String? = null,

    val userType: UserType,

    val students: List<Student>,

    val systemSpecificIds: Map<String, String> = emptyMap()
) {
    val fullName get() = "$lastName $firstName${if (middleName != null) " $middleName" else ""}"
}