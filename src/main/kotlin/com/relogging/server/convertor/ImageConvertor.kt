package com.relogging.server.convertor

import com.relogging.server.dto.response.ImageResponse
import com.relogging.server.entity.Image
import com.relogging.server.entity.NewsArticle

object ImageConvertor {
    fun toEntity(
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

    fun toResponse(
        image: Image?
    ): ImageResponse? {
        return image?.let {
            ImageResponse(
                id = it.id!!,
                url = it.url,
                caption = it.caption,
                orderIndex = it.orderIndex
            )
        }
    }
}
