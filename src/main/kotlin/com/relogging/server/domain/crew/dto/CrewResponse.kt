package com.relogging.server.domain.crew.dto

import com.relogging.server.domain.crew.entity.MeetingFrequency
import com.relogging.server.domain.crew.entity.RecruitmentType

data class CrewResponse(
    val id: Long,
    val name: String,
    val description: String,
    val leaderName: String,
    val activityRegion: String,
    val maxMembers: Int,
    val specialNotes: String?,
    val meetingFrequency: MeetingFrequency,
    val meetingCount: Int,
    val contact: String,
    val recruitmentType: RecruitmentType,
    val images: List<String>,
)
