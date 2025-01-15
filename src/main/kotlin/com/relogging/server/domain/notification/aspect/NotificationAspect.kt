package com.relogging.server.domain.notification.aspect

import com.relogging.server.domain.notification.annotation.SendNotification
import com.relogging.server.domain.notification.service.NotificationService
import com.relogging.server.domain.user.entity.User
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class NotificationAspect(
    private val notificationService: NotificationService,
) {
    @Pointcut("@annotation(com.relogging.server.domain.notification.annotation.SendNotification)")
    fun sendNoti() {
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

    @Around("sendNoti()")
    fun sendNotification(joinPoint: ProceedingJoinPoint): Any {
        val method = (joinPoint.signature as MethodSignature).method
        val sendNotification =
            method.getAnnotation(SendNotification::class.java)
                ?: throw IllegalStateException("SendNotification 어노테이션이 존재하지 않습니다.")
        val args = joinPoint.args

        if (args.isEmpty() || args[0] !is User) {
            throw IllegalArgumentException("첫 번째 인자는 반드시 User 타입이어야 합니다.")
        }

        val result =
            try {
                joinPoint.proceed()
            } catch (ex: Exception) {
                throw ex
            }

        val receiver = args[0] as User
        val type = sendNotification.type

        this.notificationService.createNotification(receiver, type)
        this.notificationService.sendNotification(receiver, type)

        return result
    }
}
