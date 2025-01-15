package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.CommentConverter
import com.relogging.server.domain.comment.dto.CommentCreateRequest
import com.relogging.server.domain.comment.dto.CommentUpdateRequest
import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.comment.repository.CommentRepository
import com.relogging.server.domain.notification.annotation.SendNotification
import com.relogging.server.domain.notification.entity.NotificationType
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
    @SendNotification(NotificationType.COMMENT)
    @Transactional
    override fun createComment(
        user: User,
        meetupId: Long,
        request: CommentCreateRequest,
    ): Long {
        val ploggingMeetup = ploggingMeetupService.getMeetupEntity(meetupId)
        val comment = CommentConverter.toEntity(ploggingMeetup, request, user)
        println("뭐임???")
        return commentRepository.save(comment).id!!
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
    }

    @Transactional
    @SendNotification(NotificationType.REPLY)
    override fun createReply(
        user: User,
        meetupId: Long,
        parentCommentId: Long,
        request: CommentCreateRequest,
    ): Long {
        val parentComment = getCommentById(parentCommentId)
        parentComment.validateReplyDepth()
        checkMeetupCommentMatch(meetupId, parentComment)

        val reply = CommentConverter.toEntity(parentComment.ploggingMeetup!!, request, user)
        parentComment.addReply(reply)

        return commentRepository.save(reply).id!!
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
