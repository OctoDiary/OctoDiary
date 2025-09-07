package org.bxkr.octodiary.data.datasource.remote

import org.bxkr.octodiary.data.model.api.mes.profile.ProfileResponse
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.UchebnikToken

interface MesLikeRemoteDataSource {
    suspend fun getProfile(accessToken: MesToken): Result<ProfileResponse>

    suspend fun toSchoolToken(uchebnikToken: UchebnikToken): Result<MesToken>
}