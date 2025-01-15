package com.relogging.server.domain.notification.annotation

import com.relogging.server.domain.notification.entity.NotificationType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SendNotification(
    val type: NotificationType,
)
