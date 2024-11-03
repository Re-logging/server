package com.relogging.server.domain.comment.dto

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.plogging.entity.PloggingEvent
import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup
import com.relogging.server.domain.user.entity.User

object CommentConverter {
    fun toEntity(
        ploggingEvent: PloggingEvent,
        request: CommentCreateRequest,
        user: User,
    ): Comment {
        val comment =
            Comment(
                ploggingEvent = ploggingEvent,
                content = request.content,
                user = user,
            )
        ploggingEvent.commentList.add(comment)
        return comment
    }

    fun toEntity(
        ploggingMeetup: PloggingMeetup,
        request: CommentCreateRequest,
        user: User,
    ): Comment {
        val comment =
            Comment(
                ploggingMeetup = ploggingMeetup,
                content = request.content,
                user = user,
            )
        ploggingMeetup.commentList.add(comment)
        return comment
    }

    fun toResponse(entity: Comment) =
        CommentResponse(
            id = entity.id!!,
            isDeleted = entity.isDeleted,
            content = if (entity.isDeleted) "삭제된 댓글입니다" else entity.content,
            authorId = entity.user.id!!,
            authorName = entity.user.nickname,
            authorImageUrl = entity.user.profileImage,
            modifiedAt = entity.updateAt,
        )
}
