package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.CommentConverter
import com.relogging.server.domain.comment.dto.ReportCommentRequest
import com.relogging.server.domain.comment.repository.CommentRepository
import com.relogging.server.domain.comment.repository.ReportRepository
import com.relogging.server.domain.user.entity.User
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportCommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val reportRepository: ReportRepository,
) : ReportCommentService {
    @Transactional
    override fun report(
        commentId: Long,
        request: ReportCommentRequest,
        user: User,
    ): Long {
        val comment =
            commentRepository.findById(commentId)
                .orElseThrow { GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND) }
        val report = CommentConverter.toReport(request, comment, user)
        return reportRepository.save(report).id!!
    }
}
