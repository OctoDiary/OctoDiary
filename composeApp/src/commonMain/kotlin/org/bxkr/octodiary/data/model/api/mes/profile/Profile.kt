package org.bxkr.octodiary.data.model.api.mes.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("birth_date")
    val birthDate: String,
//    @SerialName("email")
//    val email: Any,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("id")
    val id: Int,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String?,
//    @SerialName("phone")
//    val phone: Any,
    @SerialName("sex")
    val sex: String,
    @SerialName("snils")
    val snils: String,
    @SerialName("type")
    val type: String,
    @SerialName("user_id")
    val userId: Int
) {
    val fullName
        get() = "$lastName $firstName${middleName?.let { " $it" } ?: ""}"

    enum class UserType {
        Student,
        Parent,
        Other
    }

    val role: UserType
        get() = when (type) {
            "student" -> UserType.Student
            "parent" -> UserType.Parent
            else -> UserType.Other
        }
}