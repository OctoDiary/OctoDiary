package org.bxkr.octodiary.domain.model.visits

import kotlinx.serialization.Serializable

@Serializable
data class Visit(
    val enterTime: String,
    val exitTime: String? = null
)
