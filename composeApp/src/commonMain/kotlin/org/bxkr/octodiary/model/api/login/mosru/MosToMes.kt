package org.bxkr.octodiary.model.api.login.mosru

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MosToMes(
    @SerialName("user_authentication_for_mobile_request")
    val request: MosToMesRequest
) {
    companion object {
        @Serializable
        data class MosToMesRequest(
            @SerialName("mos_access_token")
            val mosToken: String
        )

        @Serializable
        data class MosToMesResponse(
            @SerialName("user_authentication_for_mobile_response")
            val response: MosToMesResponseInner
        )

        @Serializable
        data class MosToMesResponseInner(
            @SerialName("mesh_access_token")
            val meshAccessToken: String
        )
    }
}