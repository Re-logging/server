package com.relogging.server.service.newsArticle

import com.relogging.server.dto.request.NewsArticleRequest
import com.relogging.server.dto.response.NewsArticleListResponse
import com.relogging.server.dto.response.NewsArticleResponse
import org.springframework.web.multipart.MultipartFile

interface NewsArticleService {
    fun createNewsArticle(
        request: NewsArticleRequest,
        imageList: List<MultipartFile>,
    ): NewsArticleResponse

    fun getNewsArticle(id: Long): NewsArticleResponse

    fun getNewsArticlePage(page: Int): NewsArticleListResponse

    fun deleteNewsArticle(id: Long)
}
