package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.CommentConverter
import com.relogging.server.domain.comment.dto.CommentCreateRequest
import com.relogging.server.domain.comment.dto.CommentUpdateRequest
import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.comment.repository.CommentRepository
import com.relogging.server.domain.notification.annotation.CommentNotification
import com.relogging.server.domain.notification.annotation.ReplyNotification
import com.relogging.server.domain.ploggingMeetup.service.PloggingMeetupService
import com.relogging.server.domain.user.entity.User
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PloggingMeetupCommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val ploggingMeetupService: PloggingMeetupService,
) : PloggingMeetupCommentService {
    @CommentNotification
    @Transactional
    override fun createComment(
        user: User,
        meetupId: Long,
        request: CommentCreateRequest,
    ): Comment {
        val ploggingMeetup = ploggingMeetupService.getMeetupEntity(meetupId)
        val comment = CommentConverter.toEntity(ploggingMeetup, request, user)
        return commentRepository.save(comment)
    }

    @Transactional
    override fun updateComment(
        meetupId: Long,
        commentId: Long,
        request: CommentUpdateRequest,
        user: User,
    ): Long {
        val comment = getCommentById(commentId)
        checkMeetupCommentMatch(meetupId, comment)
        checkUserAccess(user, comment)
        comment.updateContent(request.content)
        return comment.id!!
    }

    @Transactional
    override fun deleteComment(
        meetupId: Long,
        commentId: Long,
        user: User,
    ) {
        val comment = getCommentById(commentId)
        checkMeetupCommentMatch(meetupId, comment)
        checkUserAccess(user, comment)
        comment.delete()
        comment.notification = null
    }

    @Transactional
    @ReplyNotification
    override fun createReply(
        user: User,
        meetupId: Long,
        parentCommentId: Long,
        request: CommentCreateRequest,
    ): Comment {
        val parentComment = getCommentById(parentCommentId)
        parentComment.validateReplyDepth()
        checkMeetupCommentMatch(meetupId, parentComment)

        val reply = CommentConverter.toEntity(parentComment.ploggingMeetup!!, request, user)
        parentComment.addReply(reply)

        return commentRepository.save(reply)
    }

    private fun getCommentById(id: Long) =
        commentRepository.findById(id)
            .orElseThrow { GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND) }

    private fun checkMeetupCommentMatch(
        meetupId: Long,
        comment: Comment,
    ) {
        if (comment.ploggingMeetup?.id != meetupId) {
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
