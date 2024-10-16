package com.relogging.server.dto.response

data class ImageResponse(
    val id: Long,
    val url: String,
    val caption: String?,
    val orderIndex: Int?,
)
