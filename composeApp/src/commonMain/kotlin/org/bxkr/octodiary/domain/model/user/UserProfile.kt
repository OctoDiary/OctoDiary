package org.bxkr.octodiary.domain.model.user

data class UserProfile(
    val primaryId: String,
    val diarySystemId: String,

    val firstName: String,
    val lastName: String,
    val middleName: String? = null,

    val userType: UserType,

    val systemSpecificIds: Map<String, String> = emptyMap()
)