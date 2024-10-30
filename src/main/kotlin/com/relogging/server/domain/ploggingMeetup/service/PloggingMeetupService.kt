package com.relogging.server.domain.ploggingMeetup.service

import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupListResponse
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupRequest
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

interface PloggingMeetupService {
    fun createMeetup(
        request: PloggingMeetupRequest,
        image: MultipartFile?,
    ): Long

    fun getMeetup(
        id: Long,
        increaseHits: Boolean = false,
    ): PloggingMeetupResponse

    fun getNextMeetup(currentId: Long): PloggingMeetupResponse

    fun getPrevMeetup(currentId: Long): PloggingMeetupResponse

    fun getMeetupList(pageable: Pageable): PloggingMeetupListResponse
}
