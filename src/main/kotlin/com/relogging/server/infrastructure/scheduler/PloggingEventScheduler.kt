package com.relogging.server.infrastructure.scheduler

import com.relogging.server.domain.plogging.service.PloggingEventService
import org.springframework.stereotype.Component

@Component
class PloggingEventScheduler(
    private val ploggingEventService: PloggingEventService,
) {
//    @Transactional
//    @Scheduled(cron = "0 30 3 * * *") // 매일 오전 3시 30분에 작업 수행
//    fun fetchAndSavePloggingEvent() {
//        this.ploggingEventService.fetchPloggingEventList("플로깅")
//            .then(this.ploggingEventService.fetchPloggingEventList("줍깅"))
//            .subscribe()
//    }

//    @Transactional
//    @Scheduled(cron = "0 40 3 * * *") // 매일 오전 3시 40분에 작업 수행
//    fun deleteExpiredPloggingEvents() {
//        this.ploggingEventService.deleteExpiredPloggingEvents()
//    }
}
