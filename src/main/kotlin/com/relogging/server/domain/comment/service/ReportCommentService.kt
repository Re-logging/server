package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.ReportCommentRequest
import com.relogging.server.domain.user.entity.User

interface ReportCommentService {
    fun report(
        commentId: Long,
        request: ReportCommentRequest,
        user: User,
    ): Long
}
