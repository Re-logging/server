package com.relogging.server.domain.ploggingMeetup.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

class PloggingMeetupRequest(
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
