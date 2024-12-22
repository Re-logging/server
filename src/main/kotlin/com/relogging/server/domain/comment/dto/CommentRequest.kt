package com.relogging.server.domain.comment.dto

import jakarta.validation.constraints.NotBlank

class CommentCreateRequest(
    @field:NotBlank
    val content: String,
)

class CommentUpdateRequest(
    @field:NotBlank
    val content: String,
)

class ReportCommentRequest(
    @field:NotBlank
    val reason: String,
)
