package com.relogging.server.entity

import jakarta.persistence.*

@Entity
@Table(name = "image")
class Image(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "image_id")
    val id: Long? = null,
    @field:Column(nullable = false) var url: String,
    var caption: String? = null,
    var orderIndex: Int? = null,
    @field:ManyToOne(fetch = FetchType.LAZY, targetEntity = FloggingEvent::class)
    @field:JoinColumn(name = "floggingEventId")
    var floggingEvent: FloggingEvent? = null,
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "newsArticleId")
    var newsArticle: NewsArticle? = null
)
