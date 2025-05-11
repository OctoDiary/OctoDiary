package org.bxkr.octodiary.data

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.Region

typealias StorageLatest = StorageV1

const val storageVersion = 1

@Serializable
data class StorageV1(
    val accessToken: String? = null,
    val mosRuInfo: MosRuInfo? = null,
    val regionInfo: RegionInfo? = null,
    val refreshInfo: RefreshInfo? = null
)

@Serializable
data class MosRuInfo(
    val clientId: String,
    val clientSecret: String,
    val codeVerifier: String
)

@Serializable
data class RegionInfo(
    val region: Region
)

@Serializable
data class RefreshInfo(
    val mosRuRefreshToken: String? = null
)