package com.relogging.server.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class NewsArticleRequest(
    @field:NotBlank
    val title: String?,
    @field:NotBlank
    val content: String?,
    val aiSummary: String?,
    val source: String?,
    val author: String?,
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val publishedAt: LocalDateTime?,
    val imageList: List<ImageRequest> = emptyList(),
)
