package com.relogging.server.convertor

import com.relogging.server.dto.request.PloggingEventRequest
import com.relogging.server.dto.response.PloggingEventListResponse
import com.relogging.server.dto.response.PloggingEventResponse
import com.relogging.server.entity.PloggingEvent

object PloggingEventConvertor {
    fun toListResponse(ploggingEvent: PloggingEvent): PloggingEventListResponse =
        PloggingEventListResponse(
            id = ploggingEvent.id!!,
            title = ploggingEvent.title,
            location = ploggingEvent.location,
            region = ploggingEvent.region,
            hits = ploggingEvent.hits,
            image = ImageConvertor.toResponse(ploggingEvent.imageList.getOrNull(0)),
            startDate = ploggingEvent.startDate,
            endDate = ploggingEvent.endDate,
        )

    fun toResponse(ploggingEvent: PloggingEvent): PloggingEventResponse =
        PloggingEventResponse(
            id = ploggingEvent.id!!,
            title = ploggingEvent.title,
            content = ploggingEvent.content,
            startDate = ploggingEvent.startDate,
            endDate = ploggingEvent.endDate,
            location = ploggingEvent.location,
            region = ploggingEvent.region,
            hits = ploggingEvent.hits,
            organizerName = ploggingEvent.organizerName,
            managerName = ploggingEvent.managerName,
            phoneNumber = ploggingEvent.phoneNumber,
            participationTarget = ploggingEvent.participationTarget,
            volunteerScore = ploggingEvent.volunteerScore,
            imageList =
                ploggingEvent.imageList.mapNotNull { entity ->
                    ImageConvertor.toResponse(
                        entity,
                    )
                },
        )

    fun toEntity(request: PloggingEventRequest): PloggingEvent =
        PloggingEvent(
            title = request.title,
            content = request.content,
            startDate = request.startDate,
            endDate = request.endDate,
            location = request.location,
            isVolunteerWork = request.isVolunteerWork,
            organizerName = request.organizerName,
            region = request.region,
            managerName = request.managerName,
            phoneNumber = request.phoneNumber,
            participationTarget = request.participationTarget,
            volunteerScore = request.volunteerScore,
        )
}
