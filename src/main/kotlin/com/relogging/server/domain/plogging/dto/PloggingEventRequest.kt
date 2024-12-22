package com.relogging.server.domain.plogging.dto

import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class PloggingEventRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val content: String,
    @field:NotBlank
    val startDate: LocalDate,
    @field:NotBlank
    val endDate: LocalDate,
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
    val programNumber: String,
    val url: String,
    val noticeEndDate: LocalDate,
)
