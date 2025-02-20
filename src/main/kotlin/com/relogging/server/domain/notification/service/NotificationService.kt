package com.relogging.server.domain.notification.service

import com.relogging.server.domain.notification.entity.Notification
import com.relogging.server.domain.notification.entity.NotificationType
import com.relogging.server.domain.user.entity.User

interface NotificationService {
    fun sendNotification(
        receiver: User,
        type: NotificationType,
        data: Any?,
    ): Notification

    fun getNotificationList(userId: Long): List<Notification>

    fun createNotification(
        user: User,
        type: NotificationType,
    ): Notification

    fun deleteNotification(notification: Notification?)
}
