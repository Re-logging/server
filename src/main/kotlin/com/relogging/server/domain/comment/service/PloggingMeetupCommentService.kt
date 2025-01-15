package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.CommentCreateRequest
import com.relogging.server.domain.comment.dto.CommentUpdateRequest
import com.relogging.server.domain.user.entity.User

interface PloggingMeetupCommentService {
    fun createComment(
        user: User,
        meetupId: Long,
        request: CommentCreateRequest,
    ): Long

    fun updateComment(
        meetupId: Long,
        commentId: Long,
        request: CommentUpdateRequest,
        user: User,
    ): Long

    fun deleteComment(
        meetupId: Long,
        commentId: Long,
        user: User,
    )

    fun createReply(
        user: User,
        meetupId: Long,
        parentCommentId: Long,
        request: CommentCreateRequest,
    ): Long
}
