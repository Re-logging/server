package com.relogging.server.security.dto

import com.relogging.server.domain.user.dto.UserResponse

data class OAuthLoginResponse(
    val accessToken: String,
    val userResponse: UserResponse,
)
