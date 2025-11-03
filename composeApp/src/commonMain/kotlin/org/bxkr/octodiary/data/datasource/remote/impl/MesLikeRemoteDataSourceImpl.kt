package org.bxkr.octodiary.data.datasource.remote.impl

import org.bxkr.octodiary.data.datasource.remote.MesLikeRemoteDataSource
import org.bxkr.octodiary.data.model.api.mes.profile.ProfileResponse
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.UchebnikToken
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.network.SchoolMesApiService
import org.bxkr.octodiary.network.UchebnikApiService
import org.bxkr.octodiary.network.config.SchoolMesApiConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

abstract class MesLikeRemoteDataSourceImpl(
    private val responsibleFor: DiaryId,
    private val schoolMesApiConfig: SchoolMesApiConfig,
    val uchebnikApiService: UchebnikApiService
) : MesLikeRemoteDataSource, KoinComponent {
    private val schoolMesApiService: SchoolMesApiService = get { parametersOf(schoolMesApiConfig) }

    override suspend fun getProfile(accessToken: MesToken): Result<ProfileResponse> =
        schoolMesApiService.getProfile(accessToken)

    override suspend fun toSchoolToken(uchebnikToken: UchebnikToken): Result<MesToken> =
        uchebnikApiService.toSchoolToken(uchebnikToken.value, uchebnikToken.profileId).map { MesToken(it) }
}