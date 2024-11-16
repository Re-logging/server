package com.relogging.server.infrastructure.redis.service

import com.relogging.server.infrastructure.redis.entity.RefreshToken

interface RefreshTokenService {
    fun saveRefreshToken(
        userId: Long,
        refreshToken: String,
    ): RefreshToken

    fun getRefreshToken(userId: Long): RefreshToken?

    fun validRefreshToken(
        userId: Long,
        refreshTokenValue: String,
    ): Boolean
}
