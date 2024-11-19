package com.relogging.server.infrastructure.admin.controller

import com.relogging.server.domain.newsArticle.dto.NewsArticleRequest
import com.relogging.server.domain.newsArticle.dto.NewsArticleResponse
import com.relogging.server.domain.newsArticle.service.NewsArticleService
import com.relogging.server.domain.plogging.dto.PloggingEventRequest
import com.relogging.server.domain.plogging.dto.PloggingEventResponse
import com.relogging.server.domain.plogging.service.PloggingEventService
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupResponse
import com.relogging.server.domain.ploggingMeetup.service.PloggingMeetupService
import com.relogging.server.infrastructure.scraping.service.NewsArticleScrapingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "admin 컨트롤러")
class AdminController(
    private val newsArticleScrapingService: NewsArticleScrapingService,
    private val newsArticleService: NewsArticleService,
    private val ploggingEventService: PloggingEventService,
    private val ploggingMeetupService: PloggingMeetupService,
) {
    @Operation(summary = "뉴스 아티클 생성하기", description = "뉴스가 100자 미만이면 AI 요약을 하지 않습니다.")
    @PostMapping("/newsArticles", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createArticle(
        @RequestPart request: @Valid NewsArticleRequest,
        @RequestPart(value = "image") image: MultipartFile,
    ): ResponseEntity<NewsArticleResponse> {
        val response: NewsArticleResponse = newsArticleService.createNewsArticle(request, image)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "뉴스 아티클 삭제하기")
    @DeleteMapping("/newsArticles/{id}")
    fun deleteNewsArticle(
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        newsArticleService.deleteNewsArticle(id)
        return ResponseEntity.ok("뉴스 아티클 삭제 성공했습니다")
    }

    @Operation(summary = "뉴스 아티클 크롤링하기")
    @PostMapping("/newsArticles/crawl")
    fun startCrawling(): ResponseEntity<String> {
        var count = newsArticleScrapingService.scrapEconomyNews().count()
        count += newsArticleScrapingService.scrapEconomyNews().count()
        return ResponseEntity.ok("뉴스 아티클 $count 개 크롤링 성공했습니다")
    }

    @Operation(summary = "플로깅 행사 생성하기")
    @PostMapping("/ploggingEvent", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPloggingEvent(
        @RequestPart request: @Valid PloggingEventRequest,
        @RequestPart(value = "image", required = false) image: MultipartFile?,
    ): ResponseEntity<PloggingEventResponse> {
        val response: PloggingEventResponse =
            this.ploggingEventService.createPloggingEvent(request, image)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "플로깅 행사 삭제하기")
    @DeleteMapping("/ploggingEvent/{id}")
    fun deletePloggingEvent(
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        this.ploggingEventService.deletePloggingEvent(id)
        return ResponseEntity.ok("플로깅 행사 삭제에 성공했습니다")
    }

    @Operation(summary = "플로깅 모임 조회하기", description = "조회수를 증가시키지 않습니다.")
    @GetMapping("/ploggingMeetup/{id}")
    fun getPloggingMeetup(
        @PathVariable id: Long,
    ): ResponseEntity<PloggingMeetupResponse> {
        val response = ploggingMeetupService.getMeetup(id)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "1365 API로 플로깅 리스트 정보 가져오기")
    @GetMapping("/1365Api/list")
    fun fetchPloggingEventList() {
        val startTime = System.currentTimeMillis()

        this.ploggingEventService.fetchPloggingEventList("플로깅")
            .then(this.ploggingEventService.fetchPloggingEventList("줍깅"))
            .doOnTerminate {
                val stopTime = System.currentTimeMillis()
                println("모든 작업 완료. 실행 시간: ${stopTime - startTime} ms")
            }
    }

    @Operation(summary = "모집기한 넘은 데이터 삭제")
    @DeleteMapping("/ploggingEvents")
    fun deleteExpiredPloggingEvents() {
        this.ploggingEventService.deleteExpiredPloggingEvents()
    }
}
