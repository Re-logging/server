package com.relogging.server.infrastructure.admin.dto

import jakarta.validation.constraints.NotBlank

data class AdminRequest(
    @field:NotBlank
    val code: String,
)
