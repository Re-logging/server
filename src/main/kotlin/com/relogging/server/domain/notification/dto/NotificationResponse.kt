package com.relogging.server.domain.notification.dto

import com.relogging.server.domain.notification.entity.NotificationType
import java.time.LocalDateTime

data class NotificationResponse(
    val type: NotificationType,
    val createdAt: LocalDateTime,
)
