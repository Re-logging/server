package com.relogging.server.redis.service

import com.relogging.server.redis.entity.RefreshToken

interface RefreshTokenService {
    fun saveRefreshToken(
        userId: Long,
        refreshToken: String,
    ): RefreshToken
}
