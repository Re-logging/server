package com.relogging.server.convertor

import com.relogging.server.dto.response.ImageResponse
import com.relogging.server.entity.Image
import com.relogging.server.entity.NewsArticle
import com.relogging.server.entity.PloggingEvent

object ImageConvertor {
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
