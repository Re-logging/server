package com.relogging.server.domain.ploggingMeetup.dto

import com.relogging.server.domain.comment.dto.CommentConverter
import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup
import com.relogging.server.domain.user.entity.User
import com.relogging.server.domain.utils.coordinate.dto.CoordinateConverter
import org.springframework.data.domain.Page

object PloggingMeetupConverter {
    fun toEntity(
        request: PloggingMeetupCreateRequest,
        imageUrl: String?,
        user: User,
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
            host = user,
            imageUrl = imageUrl,
            coordinate = CoordinateConverter.toEntity(request.coordinate),
        )
    }

    fun toResponse(
        entity: PloggingMeetup,
        rootComments: List<Comment>,
    ): PloggingMeetupResponse {
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
            coordinate = CoordinateConverter.toResponse(entity.coordinate),
            commentList = rootComments.map { CommentConverter.toResponse(it) },
        )
    }

    private fun toSimpleResponse(entity: PloggingMeetup): PloggingMeetupSimpleResponse {
        return PloggingMeetupSimpleResponse(
            id = entity.id!!,
            title = entity.title,
            location = entity.location,
            region = entity.region,
            startDate = entity.startDate,
            endDate = entity.endDate,
            imageUrl = entity.imageUrl,
            activityHours = entity.activityHours,
            hits = entity.hits,
        )
    }

    fun toResponse(entities: Page<PloggingMeetup>): PloggingMeetupListResponse {
        return PloggingMeetupListResponse(
            totalElements = entities.totalElements,
            totalPage = entities.totalPages,
            ploggingMeetupSimpleResponseList = entities.content.map { toSimpleResponse(it) },
        )
    }
}
