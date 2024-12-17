package com.relogging.server.infrastructure.scheduler

import com.relogging.server.infrastructure.admin.service.AdminAuthService
import com.relogging.server.infrastructure.admin.service.AdminService
import com.relogging.server.infrastructure.kakao.service.KakaoMessageService
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AdminScheduler(
    private val adminService: AdminService,
    private val adminAuthService: AdminAuthService,
    private val kakaoMessageService: KakaoMessageService,
) {
    @Scheduled(cron = "0 0 8 * * *") // 매일 오전 8시
    fun sendMorningMessage() {
        val admins = adminService.findAll()
        admins.forEach { admin ->
            kakaoMessageService.sendMemo(
                accessToken = admin.accessToken,
                message = "좋은 아침입니다!",
            )
        }
    }

    @Scheduled(cron = "0 0 18 * * *") // 매일 오후 6시
    @Async
    fun sendEveningMessage() {
        val admins = adminService.findAll()
        admins.forEach { admin ->
            kakaoMessageService.sendMemo(
                accessToken = admin.accessToken,
                message = "좋은 저녁입니다!",
            )
        }
    }

    @Scheduled(cron = "0 0 */1 * * *") // 매 시간마다
    @Async
    fun refreshAdminTokens() {
        val admins = adminService.findAll()
        admins.forEach { admin ->
            adminAuthService.kakaoTokenRefresh(admin)
        }
    }
}
