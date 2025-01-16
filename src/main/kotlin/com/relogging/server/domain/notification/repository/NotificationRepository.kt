package com.relogging.server.domain.notification.repository

import com.relogging.server.domain.notification.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findAllByUserIdOrderByCreateAtDesc(userId: Long): List<Notification>
}
