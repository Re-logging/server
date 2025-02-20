package com.relogging.server.domain.notification.controller

import com.relogging.server.domain.notification.dto.NotificationConverter
import com.relogging.server.domain.notification.dto.NotificationResponse
import com.relogging.server.domain.notification.service.NotificationService
import com.relogging.server.security.details.PrincipalDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notification")
@Tag(name = "Notification")
class NotificationController(
    private val notificationService: NotificationService,
) {
    @Operation(summary = "내가 받은 알림 리스트 가져오기")
    @GetMapping
    fun getMyNotificationList(
        @AuthenticationPrincipal principal: PrincipalDetails,
    ): ResponseEntity<List<NotificationResponse>> {
        val notificationList = this.notificationService.getNotificationList(principal.user.id!!)
        return ResponseEntity.ok(notificationList.map { NotificationConverter.toResponse(it) })
    }
}
