package org.bxkr.octodiary.data.mapper.mes

import org.bxkr.octodiary.data.model.api.mes.profile.Children
import org.bxkr.octodiary.data.model.api.mes.profile.Profile
import org.bxkr.octodiary.data.model.api.mes.profile.ProfileResponse
import org.bxkr.octodiary.domain.model.student.Student
import org.bxkr.octodiary.domain.model.user.UserProfile
import org.bxkr.octodiary.domain.model.user.UserType

fun ProfileResponse.toDomain(): UserProfile = UserProfile(
    primaryId = profile.userId.toString(),
    secondaryId = profile.id.toString(),
    firstName = profile.firstName,
    lastName = profile.lastName,
    middleName = profile.middleName,
    userType = when (profile.role) {
        Profile.UserType.Student, Profile.UserType.Other -> UserType.Student
        Profile.UserType.Parent -> UserType.Parent
    },
    students = children.map { it.toDomain() })

fun Children.toDomain(): Student = Student(
    studentId = id.toString(),
    firstName = firstName,
    lastName = lastName,
    middleName = middleName
)