package com.relogging.server.dto.response

import java.time.LocalDateTime

data class NewsArticleResponse(
    val id: Long,
    val title: String,
    val content: String?,
    val aiSummary: String?,
    val source: String?,
    val author: String?,
    val publishedAt: LocalDateTime?,
    val hits: Long,
    val imageList: List<ImageResponse>,
)
