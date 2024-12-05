package com.relogging.server.domain.newsArticle.dto

import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class NewsArticleRequest(
    @field:NotBlank
    val title: String?,
    @field:NotBlank
    val content: String?,
    val aiSummary: String?,
    val source: String?,
    val author: String?,
    val publishedAt: LocalDate?,
    val imageCaption: String? = "",
)
