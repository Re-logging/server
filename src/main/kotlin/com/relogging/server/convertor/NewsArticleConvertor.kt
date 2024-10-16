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
            imageList = newsArticle.imageList.map { ImageConvertor.toResponse(it) },
        )

    private fun toSimpleResponse(response: NewsArticle): NewsArticleSimpleResponse =
        NewsArticleSimpleResponse(
            id = response.id!!,
            title = response.title,
            publishedAt = response.publishedAt,
            imagePath = response.imageList.getOrNull(0)?.url,
        )

    fun toResponse(response: Page<NewsArticle>): NewsArticleListResponse =
        NewsArticleListResponse(
            totalElements = response.totalElements,
            totalPage = response.totalPages,
            newsArticleSimpleResponseList = response.content.map { toSimpleResponse(it) },
        )
}
