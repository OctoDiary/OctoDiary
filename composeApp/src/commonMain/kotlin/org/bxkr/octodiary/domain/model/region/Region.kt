package org.bxkr.octodiary.domain.model.region

import kotlinx.serialization.Serializable

@Serializable
data class Region(
    val regionCode: String,
    val name: String
)
