package com.relogging.server.infrastructure.admin.dto

import com.relogging.server.infrastructure.admin.entity.Admin
import com.relogging.server.infrastructure.kakao.dto.KakaoTokenResponse

object AdminConverter {
    fun toEntity(
        kakaoId: Long,
        tokens: KakaoTokenResponse,
    ): Admin {
        return Admin(
            kakaoId = kakaoId,
            accessToken = tokens.accessToken,
            expiresIn = tokens.expiresIn,
            refreshToken = tokens.refreshToken,
            refreshTokenExpiresIn = tokens.refreshTokenExpiresIn,
        )
    }
}
