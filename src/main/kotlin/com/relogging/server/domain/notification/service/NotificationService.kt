package com.relogging.server.domain.notification.service

import com.relogging.server.domain.notification.entity.NotificationType
import com.relogging.server.domain.user.entity.User

interface NotificationService {
    fun sendNotification(
        receiver: User,
        type: NotificationType,
    )
}
