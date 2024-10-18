package com.relogging.server.controller

import com.relogging.server.service.CrawlingService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/crawling/newsArticles")
@Tag(name = "News Article", description = "크롤링 관련 API")
class CrawlingController(
    private val crawlingService: CrawlingService,
) {
    @PostMapping("/crawl")
    fun startCrawling(): ResponseEntity<String> {
        crawlingService.crawlAndSaveNewsArticles()
        return ResponseEntity.ok("크롤링을 성공적으로 시작했습니다")
    }
}
