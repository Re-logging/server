package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.CommentCreateRequest
import com.relogging.server.domain.comment.dto.CommentUpdateRequest
import com.relogging.server.domain.user.entity.User

interface PloggingMeetupCommentService {
    fun createComment(
        meetupId: Long,
        request: CommentCreateRequest,
        user: User,
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
        meetupId: Long,
        parentCommentId: Long,
        request: CommentCreateRequest,
        user: User,
    ): Long
}
