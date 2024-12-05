package com.relogging.server.domain.comment.dto

import jakarta.validation.constraints.NotBlank

class CommentCreateRequest(
    @field:NotBlank
    val content: String,
)

data class CommentUpdateRequest(
    @field:NotBlank
    val content: String,
)
