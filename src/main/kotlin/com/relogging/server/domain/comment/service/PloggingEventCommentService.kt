package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.CommentCreateRequest
import com.relogging.server.domain.comment.dto.CommentUpdateRequest
import com.relogging.server.domain.user.entity.User

interface PloggingEventCommentService {
    fun createComment(
        user: User,
        eventId: Long,
        request: CommentCreateRequest,
    ): Long

    fun updateComment(
        eventId: Long,
        commentId: Long,
        request: CommentUpdateRequest,
        user: User,
    ): Long

    fun deleteComment(
        eventId: Long,
        commentId: Long,
        user: User,
    )

    fun createReply(
        user: User,
        eventId: Long,
        parentCommentId: Long,
        request: CommentCreateRequest,
    ): Long
}
