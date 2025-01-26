package com.relogging.server.domain.plogging.dto

import com.relogging.server.domain.comment.dto.CommentResponse
import com.relogging.server.domain.image.dto.ImageResponse
import com.relogging.server.domain.utils.coordinate.dto.CoordinateResponse
import java.time.LocalDate

data class PloggingEventResponse(
    val id: Long,
    val title: String,
    val content: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val location: String,
    val region: String,
    val hits: Long,
    val organizerName: String,
    val managerName: String,
    val phoneNumber: String,
    val imageList: List<ImageResponse>,
    val commentList: List<CommentResponse>,
    val url: String,
    val coordinate: CoordinateResponse,
)

data class PloggingEventListResponse(
    val id: Long,
    val title: String,
    val location: String,
    val region: String,
    val hits: Long,
    val image: ImageResponse?,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
