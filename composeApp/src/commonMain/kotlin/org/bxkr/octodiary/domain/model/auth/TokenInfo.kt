package org.bxkr.octodiary.domain.model.auth

import org.bxkr.octodiary.domain.model.user.UserType
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

sealed class TokenInfo {
    data object Loading : TokenInfo()
    data class Standard @OptIn(ExperimentalTime::class) constructor(
        val furtherLoading: Boolean,
        val canLogIn: Boolean? = null,
        val actualDiaryName: String? = null,
        val expiryTime: Instant? = null,
        val issueTime: Instant? = null,
        val userId: String? = null,
        val cannotLoadFurther: Boolean = false
    ) : TokenInfo()
    data class Extended(
        val standardInfo: Standard,
        val userName: String,
        val studentName: String? = null,
        val schoolName: String? = null,
        val role: UserType? = null,
    ) : TokenInfo()
    data object Unsupported : TokenInfo()
}