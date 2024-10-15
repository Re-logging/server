package com.relogging.server.convertor

import com.relogging.server.dto.request.NewsArticleRequest
import com.relogging.server.dto.response.NewsArticleResponse
import com.relogging.server.entity.NewsArticle

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
}
