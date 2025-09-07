package org.bxkr.octodiary.domain.model.auth

import kotlinx.serialization.Serializable
import org.bxkr.octodiary.domain.model.DiaryId

@Serializable
abstract class AccessCredentials(
    val responsibleFor: DiaryId
)