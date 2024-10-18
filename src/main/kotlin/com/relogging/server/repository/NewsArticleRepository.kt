package com.relogging.server.repository

import com.relogging.server.entity.NewsArticle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NewsArticleRepository : JpaRepository<NewsArticle, Long> {
    @Query("SELECT na.title FROM NewsArticle na")
    fun findAllTitles(): List<String>
}
