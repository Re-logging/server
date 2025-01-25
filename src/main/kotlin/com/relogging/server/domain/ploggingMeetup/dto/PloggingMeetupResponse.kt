package com.relogging.server.domain.ploggingMeetup.dto

import com.relogging.server.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class PloggingMeetupResponse(
    val id: Long,
    val title: String,
    val content: String,
    val location: String,
    val region: String,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val participantTarget: String,
    val supportDetails: String,
    val activityHours: String,
    val contactPerson: String,
    val contactNumber: String,
    val registrationLink: String?,
    val imageUrl: String?,
    val hits: Long,
    val commentList: List<CommentResponse>,
)

data class PloggingMeetupSimpleResponse(
    val id: Long,
    val title: String,
    val region: String,
    val location: String,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val imageUrl: String?,
    val activityHours: String,
    val hits: Long,
)

data class PloggingMeetupListResponse(
    val totalPage: Int,
    val totalElements: Long,
    val ploggingMeetupSimpleResponseList: List<PloggingMeetupSimpleResponse>,
)
