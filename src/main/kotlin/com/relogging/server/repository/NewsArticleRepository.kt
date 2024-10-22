package com.relogging.server.repository

import com.relogging.server.entity.NewsArticle
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface NewsArticleRepository : JpaRepository<NewsArticle, Long> {
    @Query("SELECT na.title FROM NewsArticle na")
    fun findAllTitles(): List<String>

    fun findAllByOrderByPublishedAtDescIdAsc(pageable: Pageable): Page<NewsArticle>

    fun findFirstByIdLessThanOrderByIdDesc(id: Long): Optional<NewsArticle>

    fun findFirstByIdGreaterThanOrderByIdAsc(id: Long): Optional<NewsArticle>
}
