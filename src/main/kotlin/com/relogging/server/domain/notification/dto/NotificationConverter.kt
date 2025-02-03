package com.relogging.server.domain.notification.dto

import com.relogging.server.domain.notification.entity.Notification
import com.relogging.server.domain.notification.entity.NotificationType
import com.relogging.server.domain.user.entity.User

object NotificationConverter {
    fun toResponse(notification: Notification): NotificationResponse =
        when (notification.type) {
            NotificationType.COMMENT ->
                CommentNotificationResponse(
                    createdAt = notification.createAt,
                    ploggingMeetupId = notification.requireComment().requirePloggingMeetup().id!!,
                )

            NotificationType.REPLY ->
                ReplyNotificationResponse(
                    createdAt = notification.createAt,
                    parentCommentId = notification.requireComment().requireParentComment().id!!,
                )
        }

    fun toEntity(
        user: User,
        type: NotificationType,
    ): Notification =
        Notification(
            user = user,
            type = type,
        )
}
