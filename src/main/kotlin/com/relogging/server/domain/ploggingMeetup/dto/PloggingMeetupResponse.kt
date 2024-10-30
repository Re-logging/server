package com.relogging.server.domain.ploggingMeetup.dto

import java.time.LocalDateTime

data class PloggingMeetupResponse(
    val id: Long,
    val title: String,
    val content: String,
    val location: String,
    val region: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val participantTarget: String,
    val supportDetails: String,
    val activityHours: String,
    val contactPerson: String,
    val contactNumber: String,
    val registrationLink: String?,
    val imageUrl: String?,
    val hits: Long,
)
