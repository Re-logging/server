package com.relogging.server.controller

import com.relogging.server.dto.request.NewsArticleRequest
import com.relogging.server.dto.response.NewsArticleResponse
import com.relogging.server.service.CrawlingService
import com.relogging.server.service.newsArticle.NewsArticleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
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
    private val crawlingService: CrawlingService,
    private val newsArticleService: NewsArticleService,
) {
    @Operation(summary = "뉴스 아티클 생성하기", description = "뉴스가 100자 미만이면 AI 요약을 하지 않습니다.")
    @PostMapping("/newsArticles/", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
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
        val count = crawlingService.crawlAndSaveNewsArticles()
        return ResponseEntity.ok("뉴스 아티클 $count 개 크롤링 성공했습니다")
    }
}
