package com.relogging.server.domain.user.dto

import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile

data class UpdateProfileRequest(
    @field:NotBlank
    val nickname: String,
    val image: MultipartFile?,
)
