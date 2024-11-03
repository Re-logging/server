package com.relogging.server.domain.comment.service

import com.relogging.server.domain.comment.dto.CommentConverter
import com.relogging.server.domain.comment.dto.CommentCreateRequest
import com.relogging.server.domain.comment.repository.CommentRepository
import com.relogging.server.domain.plogging.service.PloggingEventService
import com.relogging.server.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val ploggingEventService: PloggingEventService,
) : CommentService {
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
}
