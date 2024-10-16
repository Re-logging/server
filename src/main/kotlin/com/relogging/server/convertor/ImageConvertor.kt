package com.relogging.server.convertor

import com.relogging.server.dto.request.ImageRequest
import com.relogging.server.dto.response.ImageResponse
import com.relogging.server.entity.Image
import com.relogging.server.entity.NewsArticle

object ImageConvertor {
    fun toEntity(
        imageRequest: ImageRequest,
        filePath: String,
        article: NewsArticle,
    ): Image =
        Image(
            url = filePath,
            caption = imageRequest.caption,
            orderIndex = imageRequest.orderIndex!!,
            newsArticle = article,
        )

    fun toResponse(image: Image): ImageResponse =
        ImageResponse(
            id = image.id!!,
            url = image.url,
            caption = image.caption,
            orderIndex = image.orderIndex,
        )
}
