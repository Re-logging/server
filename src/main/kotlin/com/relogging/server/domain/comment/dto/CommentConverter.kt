package com.relogging.server.domain.comment.dto

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.plogging.entity.PloggingEvent
import com.relogging.server.domain.user.entity.User

object CommentConverter {
    fun toEntity(
        ploggingEvent: PloggingEvent,
        request: CommentCreateRequest,
        user: User,
    ) = Comment(
        ploggingEvent = ploggingEvent,
        content = request.content!!,
        user = user,
    )

    fun toResponse(entity: Comment) =
        CommentResponse(
            id = entity.id!!,
            content = entity.content,
            authorId = entity.user.id!!,
            authorName = entity.user.name,
            authorImageUrl = entity.user.profileImage,
            modifiedAt = entity.updateAt,
        )
}
