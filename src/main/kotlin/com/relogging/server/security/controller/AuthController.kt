package com.relogging.server.security.controller

import com.relogging.server.security.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API")
class AuthController(
    private val authService: AuthService,
) {
    @Operation(summary = "Refresh 토큰 재발급 받기")
    @PostMapping("/reissue")
    fun reissue(
        @CookieValue(value = "refreshToken", required = true) refreshToken: String,
    ): ResponseEntity<String> {
        val accessToken: String = this.authService.reissue(refreshToken)
        return ResponseEntity.ok(accessToken)
    }
}
