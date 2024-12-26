package com.relogging.server.domain.ploggingMeetup.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

class PloggingMeetupCreateRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val content: String,
    @field:NotBlank
    val location: String,
    @field:NotBlank
    val region: String,
    @field:NotNull
    val startDate: LocalDateTime,
    @field:NotNull
    val endDate: LocalDateTime,
    @field:NotBlank
    val participantTarget: String,
    @field:NotBlank
    val supportDetails: String,
    @field:NotBlank
    val activityHours: String,
    @field:NotBlank
    val contactPerson: String,
    @field:NotBlank
    val contactNumber: String,
    @field:NotBlank
    val registrationLink: String,
)

class PloggingMeetupUpdateRequest(
    val title: String?,
    val content: String?,
    val location: String?,
    val region: String?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val participantTarget: String?,
    val supportDetails: String?,
    val activityHours: String?,
    val contactPerson: String?,
    val contactNumber: String?,
    val registrationLink: String?,
)
