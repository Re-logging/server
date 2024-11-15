package com.relogging.server.domain.user.dto

import jakarta.validation.constraints.NotBlank

data class UpdateAccountRequest(
    @field:NotBlank
    val name: String
)
