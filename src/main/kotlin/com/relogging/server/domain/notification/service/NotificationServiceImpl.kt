package com.relogging.server.domain.notification.service

import com.relogging.server.domain.notification.entity.NotificationType
import com.relogging.server.domain.user.entity.User
import com.relogging.server.infrastructure.sse.repository.SseRepository
import com.relogging.server.infrastructure.sse.service.SseEventName
import com.relogging.server.infrastructure.sse.service.SseService
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val sseService: SseService,
    private val sseRepository: SseRepository,
) : NotificationService {
    override fun sendNotification(
        receiver: User,
        type: NotificationType,
    ) {
        val eventIdList = this.sseRepository.getEventIdList(receiver.id!!)
        val eventName = this.getEventNameToNotificationType(type)
        eventIdList.forEach { this.sseService.send(it, eventName, null) }
    }

    private fun getEventNameToNotificationType(type: NotificationType): SseEventName =
        when (type) {
            NotificationType.COMMENT -> SseEventName.COMMENT
            NotificationType.REPLY -> SseEventName.REPLY
        }
}
