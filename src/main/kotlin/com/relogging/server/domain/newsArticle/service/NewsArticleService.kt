package com.relogging.server.domain.newsArticle.service

import com.relogging.server.domain.newsArticle.dto.NewsArticleListResponse
import com.relogging.server.domain.newsArticle.dto.NewsArticleRequest
import com.relogging.server.domain.newsArticle.dto.NewsArticleResponse
import com.relogging.server.domain.newsArticle.entity.NewsArticle
import org.springframework.web.multipart.MultipartFile

interface NewsArticleService {
    fun createNewsArticle(
        request: NewsArticleRequest,
        image: MultipartFile,
    ): NewsArticleResponse

    fun saveNewsArticles(newsArticles: List<NewsArticle>)

    fun findAllTitles(): List<String>

    fun getNewsArticle(
        id: Long,
        increaseHits: Boolean = false,
    ): NewsArticleResponse

    fun getPrevNewsArticle(id: Long): NewsArticleResponse

    fun getNextNewsArticle(id: Long): NewsArticleResponse

    fun getNewsArticlePage(
        page: Int,
        pageSize: Int,
    ): NewsArticleListResponse

    fun deleteNewsArticle(id: Long)
}
