package com.relogging.server.convertor

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
}
