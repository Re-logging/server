package com.relogging.server.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ImageRequest(
    @field:NotBlank
    val caption: String?,
    @field:NotBlank
    @field:Size(min = 0)
    val orderIndex: Int?,
)
