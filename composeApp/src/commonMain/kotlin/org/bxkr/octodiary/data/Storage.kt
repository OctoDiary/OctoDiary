package org.bxkr.octodiary.data

import kotlinx.serialization.Serializable

typealias StorageLatest = StorageV1

const val storageVersion = 1

@Serializable
data class StorageV1(
    val accessToken: String? = null
)