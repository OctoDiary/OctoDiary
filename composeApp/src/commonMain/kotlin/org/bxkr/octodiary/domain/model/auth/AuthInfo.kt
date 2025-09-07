package org.bxkr.octodiary.domain.model.auth

import org.bxkr.octodiary.domain.gateway.AuthGateway
import org.bxkr.octodiary.domain.model.DiaryId

data class AuthInfo(
    val gateways: Map<DiaryId, AuthGateway>
)
