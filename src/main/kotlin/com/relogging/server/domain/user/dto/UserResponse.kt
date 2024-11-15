package com.relogging.server.domain.user.dto

data class UserResponse(
    val name: String,
    val nickname: String,
    val email: String,
    val image: String?,
)
