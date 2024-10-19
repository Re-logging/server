package com.relogging.server.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import kotlin.collections.ArrayList

@Entity
@Table(name = "news_article")
class NewsArticle(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "news_article_id")
    val id: Long? = null,
    @field:Column(nullable = false)
    var title: String,
    @field:Lob
    @field:Column(columnDefinition = "TEXT", nullable = false)
    var content: String,
    @field:Lob
    @field:Column(columnDefinition = "TEXT")
    var aiSummary: String?,
    var source: String?,
    var author: String?,
    var publishedAt: LocalDate?,
    @field:Column(columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    var hits: Long = 0,
    @field:OneToMany(mappedBy = "newsArticle", cascade = [CascadeType.ALL])
    var imageList: List<Image> = ArrayList(),
) : BaseEntity()
