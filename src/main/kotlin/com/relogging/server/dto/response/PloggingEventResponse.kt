package com.relogging.server.dto.response

import java.time.LocalDateTime

data class PloggingEventResponse(
    val id: Long,
    val title: String,
    val content: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val location: String,
    val region: String,
    val hits: Long,
    val organizerName: String,
    val managerName: String,
    val phoneNumber: String,
    val participationTarget: String,
    val volunteerScore: String,
    val imageList: List<ImageResponse>
)

data class PloggingEventListResponse(
    val id: Long,
    val title: String,
    val location: String,
    val region: String,
    val hits: Long,
    val image: ImageResponse?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)
