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
        user: User,
        eventId: Long,
        request: CommentCreateRequest,
    ): Comment {
        val ploggingEvent = ploggingEventService.getPloggingEventEntity(eventId)
        val comment = CommentConverter.toEntity(ploggingEvent, request, user)
        return commentRepository.save(comment)
    }

    @Transactional
    override fun updateComment(
        eventId: Long,
        commentId: Long,
        request: CommentUpdateRequest,
        user: User,
    ): Long {
        val comment = getCommentById(commentId)
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
        val comment = getCommentById(commentId)
        checkEventCommentMatch(eventId, comment)
        checkUserAccess(user, comment)
        comment.delete()
    }

    @Transactional
    override fun createReply(
        user: User,
        eventId: Long,
        parentCommentId: Long,
        request: CommentCreateRequest,
    ): Comment {
        val parentComment = getCommentById(parentCommentId)
        parentComment.validateReplyDepth()
        checkEventCommentMatch(eventId, parentComment)

        val reply = CommentConverter.toEntity(parentComment.ploggingEvent!!, request, user)
        parentComment.addReply(reply)

        return commentRepository.save(reply)
    }

    private fun getCommentById(id: Long) =
        commentRepository.findById(id)
            .orElseThrow { GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND) }

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
