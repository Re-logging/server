package com.relogging.server.domain.notification.dto

import com.relogging.server.domain.notification.entity.NotificationType
import java.time.LocalDateTime

abstract class NotificationResponse {
    abstract val type: NotificationType
    abstract val createdAt: LocalDateTime
}

data class CommentNotificationResponse(
    override val type: NotificationType = NotificationType.COMMENT,
    override val createdAt: LocalDateTime,
    val ploggingMeetupId: Long,
) : NotificationResponse()

data class ReplyNotificationResponse(
    override val type: NotificationType = NotificationType.COMMENT,
    override val createdAt: LocalDateTime,
    val parentCommeitId: Long,
) : NotificationResponse()
