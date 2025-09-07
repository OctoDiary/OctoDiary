package org.bxkr.octodiary.network

import org.bxkr.octodiary.data.model.api.mes.profile.ProfileResponse
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken

interface SchoolMesApiService {
    suspend fun getProfile(accessToken: MesToken): Result<ProfileResponse>
}