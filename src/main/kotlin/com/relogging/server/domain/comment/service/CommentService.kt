package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.CommentCreateRequest
import com.relogging.server.domain.user.entity.User

interface CommentService {
    fun createComment(
        eventId: Long,
        request: CommentCreateRequest,
        user: User,
    ): Long
}
