package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.CommentConverter
import com.relogging.server.domain.comment.dto.CommentCreateRequest
import com.relogging.server.domain.comment.dto.CommentUpdateRequest
import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.comment.repository.CommentRepository
import com.relogging.server.domain.plogging.service.PloggingEventService
import com.relogging.server.domain.user.entity.User
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PloggingEventCommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val ploggingEventService: PloggingEventService,
) : PloggingEventCommentService {
    @Transactional
    override fun createComment(
        eventId: Long,
        request: CommentCreateRequest,
        user: User,
    ): Long {
        val ploggingEvent = ploggingEventService.getPloggingEventEntity(eventId)
        val comment = CommentConverter.toEntity(ploggingEvent, request, user)
        return commentRepository.save(comment).id!!
    }

    @Transactional
    override fun updateComment(
        eventId: Long,
        commentId: Long,
        request: CommentUpdateRequest,
        user: User,
    ): Long {
        val comment = commentRepository.findById(commentId).orElseThrow { GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND) }
        checkEventCommentMatch(eventId, comment)
        checkUserAccess(user, comment)
        comment.updateContent(request.content)
        return comment.id!!
    }

    @Transactional
    override fun deleteComment(
        eventId: Long,
        commentId: Long,
        user: User,
    ) {
        val comment = commentRepository.findById(commentId).orElseThrow { GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND) }
        checkEventCommentMatch(eventId, comment)
        checkUserAccess(user, comment)
        commentRepository.delete(comment)
    }

    private fun checkEventCommentMatch(
        eventId: Long,
        comment: Comment,
    ) {
        if (comment.ploggingEvent?.id != eventId) {
            throw GlobalException(GlobalErrorCode.COMMENT_NOT_MATCH)
        }
    }

    private fun checkUserAccess(
        user: User,
        comment: Comment,
    ) {
        if (comment.user.id != user.id) {
            throw GlobalException(GlobalErrorCode.COMMENT_NOT_AUTHORIZED)
        }
    }
}
