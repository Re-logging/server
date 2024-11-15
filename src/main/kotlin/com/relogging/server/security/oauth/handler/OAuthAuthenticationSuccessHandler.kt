package com.relogging.server.security.oauth.handler

import com.relogging.server.domain.user.entity.User
import com.relogging.server.infrastructure.redis.service.RefreshTokenService
import com.relogging.server.security.details.PrincipalDetails
import com.relogging.server.security.jwt.provider.TokenProvider
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuthAuthenticationSuccessHandler(
    @Value("\${cors-allow-url.front}")
    private val frontUrl: String,
    private val tokenProvider: TokenProvider,
    private val refreshTokenService: RefreshTokenService,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val principalDetails: PrincipalDetails = authentication.principal as PrincipalDetails
        val user: User = principalDetails.user

        val accessToken: String = this.tokenProvider.createAccessToken(user.id!!)
        val refreshToken: String = this.tokenProvider.createRefreshToken(user.id)
        this.refreshTokenService.saveRefreshToken(user.id, refreshToken)

        val redirectUrl: String =
            UriComponentsBuilder.fromUriString("$frontUrl/oauth/success")
                .queryParam("accessToken", accessToken)
                .build()
                .toUriString()

        val cookie = Cookie("refreshToken", refreshToken)
        cookie.maxAge = (this.tokenProvider.refreshExpirationTime / 1000).toInt()
        cookie.isHttpOnly = true
        cookie.path = "/"
        cookie.secure = true

        response.addCookie(cookie)

        redirectStrategy.sendRedirect(request, response, redirectUrl)
    }
}
