package com.relogging.server.security.controller

import com.relogging.server.domain.user.dto.UserResponse
import com.relogging.server.domain.user.entity.User
import com.relogging.server.global.util.CookieUtils
import com.relogging.server.infrastructure.redis.service.RefreshTokenService
import com.relogging.server.security.dto.OAuthLoginRequest
import com.relogging.server.security.dto.OAuthLoginResponse
import com.relogging.server.security.dto.TempLoginRequest
import com.relogging.server.security.jwt.provider.TokenProvider
import com.relogging.server.security.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API")
class AuthController(
    private val authService: AuthService,
    private val tokenProvider: TokenProvider,
    private val refreshTokenService: RefreshTokenService,
) {
    @Operation(summary = "Refresh 토큰 재발급 받기")
    @PostMapping("/reissue")
    fun reissue(
        @CookieValue(value = "refreshToken", required = true) refreshToken: String,
    ): ResponseEntity<String> {
        val accessToken: String = this.authService.reissue(refreshToken)
        return ResponseEntity.ok(accessToken)
    }

    @Operation(summary = "OAuth 로그인")
    @PostMapping("/login")
    fun oAuthLogin(
        @RequestBody request: OAuthLoginRequest,
        response: HttpServletResponse,
    ): ResponseEntity<OAuthLoginResponse> {
        println("============ 로그인 왔냐? ==============")
        val user: User =
            this.authService.oAuthLogin(request.socialType, request.code, request.redirectUri)
        val accessToken: String = this.tokenProvider.createAccessToken(user.id!!)
        val refreshToken: String = this.tokenProvider.createRefreshToken(user.id)
        this.refreshTokenService.saveRefreshToken(user.id, refreshToken)

        val cookie =
            CookieUtils.createHttpOnlySecureCookie(
                "refreshToken",
                refreshToken,
                (this.tokenProvider.refreshExpirationTime / 1000).toInt(),
            )
        response.addCookie(cookie)

        return ResponseEntity.ok(
            OAuthLoginResponse(
                accessToken,
                UserResponse(user.name, user.email),
            ),
        )
    }

    @Operation(summary = "임시 토큰 발급")
    @PostMapping("/temp")
    fun tempLogin(
        @RequestBody request: TempLoginRequest,
    ): ResponseEntity<OAuthLoginResponse> {
        val user = this.authService.tempLogin(request.name, request.email)
        val accessToken: String = this.tokenProvider.createAccessToken(user.id!!)
        return ResponseEntity.ok(
            OAuthLoginResponse(
                accessToken,
                UserResponse(user.name, user.email),
            ),
        )
    }
}
