package com.relogging.server.entity

import jakarta.persistence.*

@Entity
@Table(name = "image")
class Image(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "image_id")
    val id: Long? = null,
    @field:Column(nullable = false)
    var url: String,
    var caption: String? = null,
    @field:Column(nullable = false)
    var orderIndex: Int,
    @field:ManyToOne(fetch = FetchType.LAZY, targetEntity = PloggingEvent::class)
    @field:JoinColumn(name = "ploggingEventId")
    var ploggingEvent: PloggingEvent? = null,
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "newsArticleId")
    var newsArticle: NewsArticle? = null,
)
