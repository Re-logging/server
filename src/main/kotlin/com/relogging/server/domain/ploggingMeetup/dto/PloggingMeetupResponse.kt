package com.relogging.server.domain.ploggingMeetup.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class PloggingMeetupResponse(
    val id: Long,
    val title: String,
    val content: String,
    val location: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val participantTarget: String,
    val supportDetails: String,
    val activityHours: String,
    val contactPerson: String,
    val contactNumber: String,
    val registrationLink: String?,
    val viewCount: Long,
    val registeredAt: LocalDateTime,
)
