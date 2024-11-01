package com.relogging.server.security.service

import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.redis.service.RefreshTokenService
import com.relogging.server.security.jwt.provider.TokenProvider
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val tokenProvider: TokenProvider,
    private val refreshTokenService: RefreshTokenService,
) : AuthService {
    override fun reissue(refreshToken: String): String {
        val userId: Long = this.tokenProvider.getUserId(refreshToken)
        if (this.refreshTokenService.validRefreshToken(userId, refreshToken)) {
            return this.tokenProvider.createAccessToken(userId)
        } else {
            throw GlobalException(GlobalErrorCode.UNAUTHORIZED)
        }
    }
}
