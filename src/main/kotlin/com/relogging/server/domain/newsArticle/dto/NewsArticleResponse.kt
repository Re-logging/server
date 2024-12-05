package com.relogging.server.domain.newsArticle.dto

import java.time.LocalDate

data class NewsArticleResponse(
    val id: Long,
    val title: String,
    val content: String?,
    val aiSummary: String?,
    val source: String?,
    val author: String?,
    val publishedAt: LocalDate?,
    val hits: Long,
    val imagePath: String?,
    val imageCaption: String?,
)

data class NewsArticleSimpleResponse(
    val id: Long,
    val title: String,
    val aiSummary: String?,
    val publishedAt: LocalDate?,
    val imagePath: String?,
)

data class NewsArticleListResponse(
    val totalPage: Int,
    val totalElements: Long,
    val newsArticleSimpleResponseList: List<NewsArticleSimpleResponse>,
)
