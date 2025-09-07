package org.bxkr.octodiary.data.model.api.mes.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("children")
    val children: List<Children>,
    @SerialName("hash")
    val hash: String,
    @SerialName("profile")
    val profile: Profile
)