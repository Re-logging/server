package com.relogging.server.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.Date
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
    var content: String,
    var aiSummary: String,
    var source: String,
    var author: String,
    var publishedDate: Date,
    @field:Column(columnDefinition = "BIGINT DEFAULT 0") var hits: Long = 0,
    @field:OneToMany(mappedBy = "newsArticle", cascade = [CascadeType.ALL])
    var imageList: List<Image> = ArrayList(),
) : BaseEntity()
