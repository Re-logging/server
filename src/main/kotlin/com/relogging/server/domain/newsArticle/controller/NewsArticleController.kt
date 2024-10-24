package com.relogging.server.domain.newsArticle.controller

import com.relogging.server.domain.newsArticle.dto.NewsArticleListResponse
import com.relogging.server.domain.newsArticle.dto.NewsArticleResponse
import com.relogging.server.domain.newsArticle.service.NewsArticleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/newsArticles")
@Tag(name = "News Article", description = "뉴스 아티클 관련 API")
class NewsArticleController(
    private val newsArticleService: NewsArticleService,
) {
    @Operation(summary = "뉴스 아티클 조회하기")
    @GetMapping("/{id}")
    fun getNewsArticle(
        @PathVariable id: Long,
    ): ResponseEntity<NewsArticleResponse> {
        val response: NewsArticleResponse = newsArticleService.getNewsArticle(id)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "이전 뉴스 아티클 조회하기")
    @GetMapping("/{id}/prev")
    fun getPrevNewsArticle(
        @PathVariable id: Long,
    ): ResponseEntity<NewsArticleResponse> {
        val response: NewsArticleResponse = newsArticleService.getPrevNewsArticle(id)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "다음 뉴스 아티클 조회하기")
    @GetMapping("/{id}/next")
    fun getNextNewsArticle(
        @PathVariable id: Long,
    ): ResponseEntity<NewsArticleResponse> {
        val response: NewsArticleResponse = newsArticleService.getNextNewsArticle(id)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "뉴스 아티클 리스트 조회하기")
    @GetMapping("/list")
    fun getNewsArticleList(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "9", required = false) pageSize: Int,
    ): ResponseEntity<NewsArticleListResponse> {
        val response: NewsArticleListResponse = newsArticleService.getNewsArticlePage(page, pageSize)
        return ResponseEntity.ok(response)
    }
}
