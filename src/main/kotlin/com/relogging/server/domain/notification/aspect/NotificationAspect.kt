package com.relogging.server.domain.notification.aspect

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.notification.entity.NotificationType
import com.relogging.server.domain.notification.service.NotificationService
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class NotificationAspect(
    private val notificationService: NotificationService,
) {
    @Pointcut("@annotation(com.relogging.server.domain.notification.annotation.CommentNotification)")
    fun commentNotification() {
    }

//    @Pointcut("execution(public * com.relogging.server.domain..controller..*(..))")
//    fun onRequest() {
//    }
//
//    @Around("onRequest()")
//    fun test(joinPoint: ProceedingJoinPoint): Any {
//        println("============================ test ====================")
//        return joinPoint.proceed()
//    }

    @AfterReturning(pointcut = "commentNotification()", returning = "comment")
    fun sendCommentNotification(comment: Comment) {
        val receiver = comment.ploggingMeetup!!.host
        val sender = comment.user

        if (receiver.id == sender.id) {
            return
        }

        val type =
            if (comment.parentComment == null) NotificationType.COMMENT else NotificationType.REPLY

        this.notificationService.sendNotification(receiver, type)
    }
}
