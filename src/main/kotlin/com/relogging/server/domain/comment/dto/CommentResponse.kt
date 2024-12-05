package com.relogging.server.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val isDeleted: Boolean,
    val content: String,
    val authorId: Long,
    val authorName: String,
    val authorImageUrl: String?,
    val modifiedAt: LocalDateTime,
    val replies: List<CommentResponse>,
)
