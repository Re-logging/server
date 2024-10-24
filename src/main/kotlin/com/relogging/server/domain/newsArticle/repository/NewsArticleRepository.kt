package com.relogging.server.domain.newsArticle.repository

import com.relogging.server.domain.newsArticle.entity.NewsArticle
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface NewsArticleRepository : JpaRepository<NewsArticle, Long> {
    @Query("SELECT na.title FROM NewsArticle na")
    fun findAllTitles(): List<String>

    fun findAllByOrderByPublishedAtDescIdAsc(pageable: Pageable): Page<NewsArticle>

    @Query(
        """
        SELECT n FROM NewsArticle n 
        WHERE (n.publishedAt < (SELECT na.publishedAt FROM NewsArticle na WHERE na.id = :id)
            OR (n.publishedAt = (SELECT na.publishedAt FROM NewsArticle na WHERE na.id = :id)
                AND n.id > :id))
        ORDER BY n.publishedAt DESC, n.id ASC
        LIMIT 1
    """,
    )
    fun findNextArticle(
        @Param("id") id: Long,
    ): Optional<NewsArticle>

    @Query(
        """
        SELECT n FROM NewsArticle n 
        WHERE (n.publishedAt > (SELECT na.publishedAt FROM NewsArticle na WHERE na.id = :id)
            OR (n.publishedAt = (SELECT na.publishedAt FROM NewsArticle na WHERE na.id = :id)
                AND n.id < :id))
        ORDER BY n.publishedAt ASC, n.id DESC
        LIMIT 1
    """,
    )
    fun findPrevArticle(
        @Param("id") id: Long,
    ): Optional<NewsArticle>
}
