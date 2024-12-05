package com.relogging.server.domain.crew.dto

import com.relogging.server.domain.crew.entity.Crew

object CrewConverter {
    fun toEntity(
        request: CrewCreateRequest,
        imagePathList: List<String>,
        leaderName: String,
    ): Crew =
        Crew(
            name = request.crewTitle,
            description = request.crewDescription,
            leaderName = leaderName,
            activityRegion = request.activityRegion,
            maxMembers = request.maxMembers,
            specialNotes = request.specialNotes,
            meetingFrequency = request.meetingFrequency,
            meetingCount = request.meetingCount ?: 0,
            contact = request.contact,
            recruitmentType = request.recruitmentType,
            mainImage = imagePathList.getOrNull(0),
            imageList = imagePathList.toMutableList(),
        )
}
