package org.bxkr.octodiary.domain.model.diary

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.region.Region

@Serializable
data class Diary(
    val id: DiaryId,
    val name: String,
    val region: Region
)
