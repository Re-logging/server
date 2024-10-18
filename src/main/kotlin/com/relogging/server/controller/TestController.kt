package com.relogging.server.controller

import com.relogging.server.service.CrawlingService
import com.relogging.server.service.image.ImageService
import com.relogging.server.service.newsArticle.NewsArticleService
import com.relogging.server.service.openai.OpenAiService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "테스트 컨트롤러")
class TestController(
    private val imageService: ImageService,
    private val openAiService: OpenAiService,
    private val crawlingService: CrawlingService,
    private val newsArticleService: NewsArticleService,
) {
    @PostMapping("/image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createArticle(
        @RequestPart(value = "file", required = true) file: List<MultipartFile>,
    ): ResponseEntity<List<String>> = ResponseEntity.ok(file.map { imageService.saveImageFile(it) })

    @PostMapping("/openai")
    fun aiSummary(
        @RequestBody content: String,
    ): ResponseEntity<String> = ResponseEntity.ok(openAiService.aiSummary(content))

    @PostMapping("/crawl")
    fun startCrawling(): ResponseEntity<String> {
        crawlingService.crawlAndSaveNewsArticles()
        return ResponseEntity.ok("크롤링을 성공적으로 시작했습니다")
    }
}
