package com.relogging.server.domain.notification.dto

import com.relogging.server.domain.notification.entity.Notification
import com.relogging.server.domain.notification.entity.NotificationType
import com.relogging.server.domain.user.entity.User

object NotificationConverter {
    fun toResponse(notification: Notification): NotificationResponse =
        NotificationResponse(
            type = notification.type,
            createdAt = notification.createAt,
        )

    fun toEntity(
        user: User,
        type: NotificationType,
    ): Notification =
        Notification(
            user = user,
            type = type,
        )
}
