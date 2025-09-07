package org.bxkr.octodiary.data.model.api.mes.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Representative(
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String,
    @SerialName("person_id")
    val personId: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("snils")
    val snils: String,
    @SerialName("type")
    val type: String,
    @SerialName("type_id")
    val typeId: Int
)