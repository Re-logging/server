package com.relogging.server.repository

import com.relogging.server.entity.NewsArticle
import org.springframework.data.jpa.repository.JpaRepository

interface NewsArticleRepository : JpaRepository<NewsArticle, Long>
