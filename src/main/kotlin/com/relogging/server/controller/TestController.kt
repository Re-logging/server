package com.relogging.server.controller

import com.relogging.server.service.image.ImageService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "테스트 컨트롤러")
class TestController(
    private val imageService: ImageService,
) {
    @PostMapping("/image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createArticle(
        @RequestPart(value = "file", required = true) file: List<MultipartFile>,
    ): ResponseEntity<List<String>> = ResponseEntity.ok(file.map { imageService.saveImageFile(it) })
}
