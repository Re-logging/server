package com.relogging.server.infrastructure.scheduler

import com.relogging.server.domain.newsArticle.service.NewsArticleService
import com.relogging.server.domain.plogging.PloggingEventSortType
import com.relogging.server.domain.plogging.service.PloggingEventService
import com.relogging.server.domain.ploggingMeetup.PloggingMeetupSortType
import com.relogging.server.domain.ploggingMeetup.service.PloggingMeetupService
import com.relogging.server.infrastructure.admin.service.AdminAuthService
import com.relogging.server.infrastructure.admin.service.AdminService
import com.relogging.server.infrastructure.kakao.service.KakaoMessageService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class AdminScheduler(
    private val adminService: AdminService,
    private val adminAuthService: AdminAuthService,
    private val kakaoMessageService: KakaoMessageService,
    private val ploggingEventService: PloggingEventService,
    private val ploggingMeetupService: PloggingMeetupService,
    private val newsArticleService: NewsArticleService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "0 0 8,18 * * *") // 매일 오전 8,18시
    @Transactional
    fun sendPromotionToKakaoMemo() {
        val admins = adminService.findAll()
        val message = buildPromotionMessage()
        admins.forEach {
            try {
                kakaoMessageService.sendMemo(
                    accessToken = it.accessToken,
                    message = message,
                )
            } catch (e: Exception) {
                log.error("Error sending memo to kakao message (admin id: ${it.id}): ", e)
            }
        }
        log.info("AdminScheduler: sendPromotionToKakaoMemo")
    }

    private fun buildPromotionMessage(): String {
        val now = LocalDateTime.now()
        var message = "⏰ ${now.month}월 ${now.dayOfMonth}일 플로깅 커뮤니티 <리로깅>의 글들\n\n"
        val baseUrl = "https://www.re-logging.com/"

        val newsArticle =
            newsArticleService.getNewsArticlePage(
                page = 0,
                pageSize = 5,
            )
        val ploggingMeetup =
            ploggingMeetupService.getMeetupList(
                page = 0,
                pageSize = 5,
                region = null,
                isOpen = true,
                sortBy = PloggingMeetupSortType.START_DATE,
                sortDirection = Sort.Direction.ASC,
            )
        val ploggingEvents =
            ploggingEventService.getPloggingEventList(
                page = 0,
                pageSize = 5,
                region = null,
                isOpen = true,
                sortBy = PloggingEventSortType.START_DATE,
                sortDirection = Sort.Direction.ASC,
            )

        ploggingMeetup.ploggingMeetupSimpleResponseList.forEach {
            message += "\uD83C\uDF31 [플로깅 모임] ${it.title}\n" +
                " - ${it.region}\n" +
                " - ${baseUrl}events/${it.id}\n"
        }
        message += "\n"
        ploggingEvents.forEach {
            message += "\uD83C\uDF31 [우리 동네 플로깅] ${it.title}\n" +
                " - ${it.region}\n" +
                " - ${baseUrl}meetup/${it.id}\n"
        }
        message += "\n"
        newsArticle.newsArticleSimpleResponseList.forEach {
            message += "\uD83C\uDF31 [환경 뉴스] ${it.title}\n" +
                " - ${baseUrl}news/${it.id}\n"
        }

        return message
    }

    @Scheduled(cron = "0 0 * * * *") // 매 시간마다
    @Transactional
    fun refreshAdminTokens() {
        val admins = adminService.findAll()
        admins.forEach {
            try {
                adminAuthService.kakaoTokenRefresh(it)
            } catch (e: Exception) {
                log.error("Error to refresh kakao tokens (id: ${it.id}) : ", e)
            }
        }
        log.info("AdminScheduler: refreshAdminTokens")
    }
}
