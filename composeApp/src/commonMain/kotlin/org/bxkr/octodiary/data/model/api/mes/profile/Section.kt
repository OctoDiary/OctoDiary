package org.bxkr.octodiary.data.model.api.mes.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Section(
    @SerialName("id")
    val id: Int,
    @SerialName("is_fake")
    val isFake: Boolean,
    @SerialName("name")
    val name: String,
//    @SerialName("subject_id")
//    val subjectId: Any
)