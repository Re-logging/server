package com.relogging.server.redis.service

import com.relogging.server.redis.entity.RefreshToken
import com.relogging.server.redis.repository.RefreshTokenRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RefreshTokenServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value("\${jwt.validity-in-seconds-refresh}")
    private val refreshExpirationTime: Long,
) : RefreshTokenService {
    override fun saveRefreshToken(
        userId: Long,
        refreshToken: String,
    ): RefreshToken {
        return this.refreshTokenRepository.save(
            RefreshToken(
                userId,
                refreshToken,
                refreshExpirationTime,
            ),
        )
    }
}
