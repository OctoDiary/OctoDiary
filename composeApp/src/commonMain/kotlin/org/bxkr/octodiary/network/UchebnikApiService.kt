package org.bxkr.octodiary.network

import org.bxkr.octodiary.data.model.auth.accesscredentials.token.MesToken
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.UchebnikToken

interface UchebnikApiService {
    suspend fun toSchoolToken(uchebnikToken: String, profileId: String): Result<String>
}