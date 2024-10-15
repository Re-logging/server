package com.relogging.server.controller

import com.relogging.server.dto.request.NewsArticleRequest
import com.relogging.server.dto.response.NewsArticleResponse
import com.relogging.server.service.newsArticle.NewsArticleService
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
@RequestMapping("/api/newsArticles")
@Tag(name = "News Article", description = "뉴스 아티클 관련 API")
class NewsArticleController(
    private val newsArticleService: NewsArticleService,
) {
    @Operation(
        summary = "뉴스 아티클 생성하기",
        description =
            "이미지와 캡션 순서를 맞춰주세요. publishedAt은 LocalDateTime 표준 형식입니다." +
                " request 부분은 application/json 명시해주어야 합니다. 스웨거에서는 작동하지 않습니다.",
    )
    @PostMapping("/", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createArticle(
        @RequestPart request: @Valid NewsArticleRequest,
        @RequestPart(value = "image") imageList: List<MultipartFile> = emptyList(),
    ): ResponseEntity<NewsArticleResponse> {
        val response: NewsArticleResponse = newsArticleService.createNewsArticle(request, imageList)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "뉴스 아티클 조회하기")
    @GetMapping("/{id}")
    fun getNewsArticle(
        @PathVariable id: Long,
    ): ResponseEntity<NewsArticleResponse> {
        val response: NewsArticleResponse = newsArticleService.getNewsArticle(id)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "뉴스 아티클 삭제하기")
    @DeleteMapping("/{id}")
    fun deleteNewsArticle(
        @PathVariable id: Long,
    ) {
        newsArticleService.deleteNewsArticle(id)
    }
}
