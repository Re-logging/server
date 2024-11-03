package com.relogging.server.domain.plogging.dto

import com.relogging.server.domain.comment.dto.CommentConverter
import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.image.dto.ImageConverter
import com.relogging.server.domain.plogging.entity.PloggingEvent

object PloggingEventConverter {
    fun toListResponse(ploggingEvent: PloggingEvent): PloggingEventListResponse =
        PloggingEventListResponse(
            id = ploggingEvent.id!!,
            title = ploggingEvent.title,
            location = ploggingEvent.location,
            region = ploggingEvent.region,
            hits = ploggingEvent.hits,
            image = ImageConverter.toResponse(ploggingEvent.imageList.getOrNull(0)),
            startDate = ploggingEvent.startDate,
            endDate = ploggingEvent.endDate,
        )

    fun toResponse(
        ploggingEvent: PloggingEvent,
        rootComments: List<Comment>,
    ): PloggingEventResponse =
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
                    ImageConverter.toResponse(
                        entity,
                    )
                },
            commentList = rootComments.map { CommentConverter.toResponse(it) },
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
