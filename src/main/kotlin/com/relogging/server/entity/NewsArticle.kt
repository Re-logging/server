package com.relogging.server.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "news_article")
class NewsArticle(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "news_article_id")
    val id: Long? = null,
    @field:Column(nullable = false) var title: String,
    var content: String,
    var aiSummary: String,
    var source: String,
    var author: String,
    var publishedDate: Date,
    @field:Column(columnDefinition = "BIGINT DEFAULT 0") var hits: Long = 0,
    @field:OneToMany(mappedBy = "newsArticle", cascade = [CascadeType.ALL])
    var imageList: List<Image> = ArrayList()
) : BaseEntity() {}
