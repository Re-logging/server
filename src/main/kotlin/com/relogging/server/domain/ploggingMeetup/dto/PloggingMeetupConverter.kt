package com.relogging.server.domain.ploggingMeetup.dto

import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup

object PloggingMeetupConverter {
    fun toEntity(
        request: PloggingMeetupRequest,
        imageUrl: String?,
    ): PloggingMeetup {
        return PloggingMeetup(
            title = request.title,
            content = request.content,
            location = request.location,
            region = request.region,
            startDate = request.startDate,
            endDate = request.endDate,
            participationTarget = request.participantTarget,
            supportDetails = request.supportDetails,
            activityHours = request.activityHours,
            contactPerson = request.contactPerson,
            contactNumber = request.contactNumber,
            registrationLink = request.registrationLink,
            imageUrl = imageUrl,
        )
    }
}
