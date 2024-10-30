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

    fun toResponse(entity: PloggingMeetup): PloggingMeetupResponse {
        return PloggingMeetupResponse(
            id = entity.id!!,
            title = entity.title,
            content = entity.content,
            location = entity.location,
            region = entity.region,
            startDate = entity.startDate,
            endDate = entity.endDate,
            participantTarget = entity.participationTarget,
            supportDetails = entity.supportDetails,
            activityHours = entity.activityHours,
            contactPerson = entity.contactPerson,
            contactNumber = entity.contactNumber,
            registrationLink = entity.registrationLink,
            imageUrl = entity.imageUrl,
            hits = entity.hits,
        )
    }
}
