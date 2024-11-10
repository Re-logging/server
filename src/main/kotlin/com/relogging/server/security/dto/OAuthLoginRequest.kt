package com.relogging.server.security.dto

import com.relogging.server.domain.user.entity.SocialType
import jakarta.validation.constraints.NotBlank

data class OAuthLoginRequest(
    @field:NotBlank
    val socialType: SocialType,
    @field:NotBlank
    val code: String,
    @field:NotBlank
    val redirectUri: String,
)
