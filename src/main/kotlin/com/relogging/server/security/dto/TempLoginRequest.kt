package com.relogging.server.security.dto

import jakarta.validation.constraints.NotBlank

data class TempLoginRequest(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val email: String,
)
