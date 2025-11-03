package org.bxkr.octodiary.domain.model.session

import org.bxkr.octodiary.domain.model.auth.AccessCredentials

data class Session(
    val accessCredentials: AccessCredentials
) {
    val diarySystemId get() = accessCredentials.responsibleFor
}
