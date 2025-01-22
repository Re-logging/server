package com.relogging.server.domain.notification.service

import com.relogging.server.domain.notification.dto.NotificationConverter
import com.relogging.server.domain.notification.entity.Notification
import com.relogging.server.domain.notification.entity.NotificationType
import com.relogging.server.domain.notification.repository.NotificationRepository
import com.relogging.server.domain.user.entity.User
import com.relogging.server.infrastructure.sse.repository.SseRepository
import com.relogging.server.infrastructure.sse.service.SseEventName
import com.relogging.server.infrastructure.sse.service.SseService
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val sseService: SseService,
    private val sseRepository: SseRepository,
    private val notificationRepository: NotificationRepository,
) : NotificationService {
    override fun sendNotification(
        receiver: User,
        type: NotificationType,
    ): Notification {
        val emitterIdList = this.sseRepository.getEmitterIdList(receiver.id!!)
        val eventName = this.getEventNameToNotificationType(type)
        emitterIdList.forEach { this.sseService.send(it, eventName, null) }
        return this.createNotification(receiver, type)
    }

    override fun getNotificationList(userId: Long): List<Notification> =
        this.notificationRepository.findAllByUserIdOrderByCreateAtDesc(userId)

    override fun createNotification(
        user: User,
        type: NotificationType,
    ): Notification {
        val notification = NotificationConverter.toEntity(user, type)
        return this.notificationRepository.save(notification)
    }

    override fun deleteNotification(notification: Notification?) {
        if (notification?.id != null) {
            this.notificationRepository.deleteById(notification.id)
        }
    }

    private fun getEventNameToNotificationType(type: NotificationType): SseEventName =
        when (type) {
            NotificationType.COMMENT -> SseEventName.COMMENT
            NotificationType.REPLY -> SseEventName.REPLY
        }
}
