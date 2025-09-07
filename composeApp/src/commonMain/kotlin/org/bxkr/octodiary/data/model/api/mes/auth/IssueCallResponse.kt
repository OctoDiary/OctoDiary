package org.bxkr.octodiary.data.model.api.mes.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueCallResponse(
    @SerialName("client_id") val clientId: String,
    @SerialName("client_secret") val clientSecret: String,
)
