package com.relogging.server.dto.request

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class PloggingEventRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val content: String,
    @field:NotBlank
    val startDate: LocalDateTime,
    @field:NotBlank
    val endDate: LocalDateTime,
    @field:NotBlank
    val location: String,
    val organizerName: String,
    @field:NotBlank
    val region: String,
    val managerName: String,
    val phoneNumber: String,
    val participationTarget: String,
    val volunteerScore: String,
    val isVolunteerWork: Boolean,
    val imageCaption: String? = "",
)
