package org.bxkr.octodiary.data.model.api.mes.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueCallBody(
    @SerialName("software_id") val softwareId: String,
    @SerialName("device_type") val deviceType: String,
    @SerialName("software_statement") val softwareStatement: String
)
