package org.bxkr.octodiary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Region(
    val regionCode: String,
    val name: String
)
