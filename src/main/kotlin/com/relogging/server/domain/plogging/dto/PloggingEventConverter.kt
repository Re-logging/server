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
//            participationTarget = ploggingEvent.participationTarget,
//            volunteerScore = ploggingEvent.volunteerScore,
            url = ploggingEvent.url,
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
//            participationTarget = request.participationTarget,
//            volunteerScore = request.volunteerScore,
            programNumber = request.programNumber,
            url = request.url,
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
        )
}
