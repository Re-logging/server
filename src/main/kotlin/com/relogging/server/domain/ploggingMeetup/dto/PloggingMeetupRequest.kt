package com.relogging.server.domain.ploggingMeetup.dto

import com.relogging.server.domain.utils.coordinate.dto.CoordinateRequest
import jakarta.validation.constraints.NotBlank
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
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
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
    val coordinate: CoordinateRequest,
)

class PloggingMeetupUpdateRequest(
    @field:NotBlank val title: String,
    @field:NotBlank val content: String,
    @field:NotBlank val location: String,
    @field:NotBlank val region: String,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    @field:NotBlank val participantTarget: String,
    @field:NotBlank val supportDetails: String,
    @field:NotBlank val activityHours: String,
    @field:NotBlank val contactPerson: String,
    @field:NotBlank val contactNumber: String,
    @field:NotBlank val registrationLink: String,
    val coordinate: CoordinateRequest,
)
