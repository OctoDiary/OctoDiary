package org.bxkr.octodiary.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthEduInfo(
    val state: String
)