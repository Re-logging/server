package com.relogging.server.domain.image.dto

data class ImageResponse(
    val id: Long,
    val url: String,
    val caption: String?,
    val orderIndex: Int?,
)
