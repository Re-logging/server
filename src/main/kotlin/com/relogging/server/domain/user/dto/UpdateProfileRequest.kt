package com.relogging.server.domain.user.dto

import jakarta.validation.constraints.NotBlank

data class UpdateProfileRequest(
    @field:NotBlank
    val nickname: String,
)
