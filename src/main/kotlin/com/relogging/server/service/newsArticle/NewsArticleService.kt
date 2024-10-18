package com.relogging.server.service.newsArticle

import com.relogging.server.dto.request.NewsArticleRequest
import com.relogging.server.dto.response.NewsArticleListResponse
import com.relogging.server.dto.response.NewsArticleResponse
import com.relogging.server.entity.NewsArticle
import org.springframework.web.multipart.MultipartFile

interface NewsArticleService {
    fun createNewsArticle(
        request: NewsArticleRequest,
        image: MultipartFile,
    ): NewsArticleResponse

    fun saveNewsArticles(newsArticles: List<NewsArticle>)

    fun findAllTitles(): List<String>

    fun getNewsArticle(id: Long): NewsArticleResponse

    fun getNewsArticlePage(
        page: Int,
        pageSize: Int,
    ): NewsArticleListResponse

    fun deleteNewsArticle(id: Long)
}
