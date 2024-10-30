package com.relogging.server.domain.ploggingMeetup.service

import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupRequest
import org.springframework.web.multipart.MultipartFile

interface PloggingMeetupService {
    fun createMeetup(
        request: PloggingMeetupRequest,
        image: MultipartFile?,
    ): Long
}
