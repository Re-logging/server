package com.relogging.server.domain.image.dto

import com.relogging.server.domain.image.entity.Image
import com.relogging.server.domain.newsArticle.entity.NewsArticle
import com.relogging.server.domain.plogging.entity.PloggingEvent

object ImageConverter {
    fun toEntityWithNews(
        filePath: String,
        caption: String?,
        article: NewsArticle,
    ): Image =
        Image(
            url = filePath,
            caption = caption,
            orderIndex = 0,
            newsArticle = article,
        )

    fun toEntityWithPloggingEvent(
        filePath: String,
        caption: String?,
        ploggingEvent: PloggingEvent,
    ): Image =
        Image(
            url = filePath,
            caption = caption,
            orderIndex = 0,
            ploggingEvent = ploggingEvent,
        )

    fun toEntity(filePath: String): Image =
        Image(
            url = filePath,
            caption = null,
            orderIndex = 0,
        )

    fun toResponse(image: Image?): ImageResponse? {
        return image?.let {
            ImageResponse(
                id = it.id!!,
                url = it.url,
                caption = it.caption,
                orderIndex = it.orderIndex,
            )
        }
    }
}
