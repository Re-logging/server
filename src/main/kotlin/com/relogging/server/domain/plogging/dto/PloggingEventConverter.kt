package com.relogging.server.domain.plogging.dto

import com.relogging.server.domain.comment.dto.CommentConverter
import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.image.dto.ImageConverter
import com.relogging.server.domain.plogging.entity.PloggingEvent
import com.relogging.server.domain.utils.coordinate.Coordinate
import com.relogging.server.domain.utils.coordinate.dto.CoordinateConverter

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
            url = ploggingEvent.url,
            coordinate = CoordinateConverter.toResponse(ploggingEvent.coordinate),
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
            noticeEndDate = request.noticeEndDate,
            location = request.location,
            organizerName = request.organizerName,
            region = request.region,
            managerName = request.managerName,
            phoneNumber = request.phoneNumber,
            programNumber = request.programNumber,
            url = request.url,
            coordinate = CoordinateConverter.toEntity(request.coordinateRequest),
        )

    fun toEntity(
        item: VolunteeringDetailApiResponseItem,
        url: String,
    ): PloggingEvent =
        PloggingEvent(
            title = item.programSubject!!,
            content = item.content!!,
            startDate = item.programBeginDate!!,
            endDate = item.programEndDate!!,
            location = item.actPlace!!,
            organizerName = item.recruitmentOrganization!!,
            region = item.registrationOrganization!!,
            managerName = item.managerName!!,
            phoneNumber = item.phoneNumber!!,
            programNumber = item.programRegistrationNumber!!,
            url = url,
            noticeEndDate = item.noticeEndDate!!,
            coordinate = Coordinate(null, null),
        )
}
