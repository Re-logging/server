package com.relogging.server.infrastructure.redis.service

import com.relogging.server.infrastructure.redis.entity.RefreshToken
import com.relogging.server.infrastructure.redis.repository.RefreshTokenRepository
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
    ): RefreshToken =
        this.refreshTokenRepository.save(
            RefreshToken(
                userId,
                refreshToken,
                refreshExpirationTime,
            ),
        )

    override fun getRefreshToken(userId: Long): RefreshToken? = this.refreshTokenRepository.findById(userId).orElse(null)

    override fun validRefreshToken(
        userId: Long,
        refreshTokenValue: String,
    ): Boolean {
        val refreshToken: RefreshToken? = this.getRefreshToken(userId)

        return refreshToken != null && refreshToken.refreshToken.equals(refreshTokenValue)
    }
}
