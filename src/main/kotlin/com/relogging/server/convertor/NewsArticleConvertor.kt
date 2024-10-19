package com.relogging.server.convertor

import com.relogging.server.dto.request.NewsArticleRequest
import com.relogging.server.dto.response.NewsArticleListResponse
import com.relogging.server.dto.response.NewsArticleResponse
import com.relogging.server.dto.response.NewsArticleSimpleResponse
import com.relogging.server.entity.NewsArticle
import org.springframework.data.domain.Page

object NewsArticleConvertor {
    fun toEntity(request: NewsArticleRequest): NewsArticle =
        NewsArticle(
            title = request.title!!,
            content = request.content!!,
            aiSummary = request.aiSummary,
            source = request.source,
            author = request.author,
            publishedAt = request.publishedAt,
        )

    fun toResponse(newsArticle: NewsArticle): NewsArticleResponse =
        NewsArticleResponse(
            id = newsArticle.id!!,
            title = newsArticle.title,
            content = newsArticle.content,
            aiSummary = newsArticle.aiSummary,
            source = newsArticle.source,
            author = newsArticle.author,
            publishedAt = newsArticle.publishedAt,
            hits = newsArticle.hits,
            imageCaption = newsArticle.imageList.getOrNull(0)?.caption,
        )

    private fun toSimpleResponse(newsArticle: NewsArticle): NewsArticleSimpleResponse =
        NewsArticleSimpleResponse(
            id = newsArticle.id!!,
            title = newsArticle.title,
            aiSummary = newsArticle.aiSummary,
            publishedAt = newsArticle.publishedAt,
            imagePath = newsArticle.imageList.getOrNull(0)?.url,
        )

    fun toResponse(newsArticles: Page<NewsArticle>): NewsArticleListResponse =
        NewsArticleListResponse(
            totalElements = newsArticles.totalElements,
            totalPage = newsArticles.totalPages,
            newsArticleSimpleResponseList = newsArticles.content.map { toSimpleResponse(it) },
        )
}
