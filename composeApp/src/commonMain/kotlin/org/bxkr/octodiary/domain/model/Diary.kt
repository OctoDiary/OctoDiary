package org.bxkr.octodiary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Diary(
    val id: DiaryId,
    val name: String,
    val region: Region
)
