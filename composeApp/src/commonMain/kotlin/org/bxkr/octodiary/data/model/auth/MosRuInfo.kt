package org.bxkr.octodiary.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class MosRuInfo(
    val clientId: String,
    val clientSecret: String,
    val codeVerifier: String
)