package org.bxkr.octodiary.domain.usecase.auth

import org.bxkr.octodiary.domain.model.diary.Diary
import org.bxkr.octodiary.domain.model.region.Region
import org.bxkr.octodiary.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class GetRegionDiariesUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(region: Region): List<Diary> {
        return authRepository.getRegionDiaries(region)
    }
}