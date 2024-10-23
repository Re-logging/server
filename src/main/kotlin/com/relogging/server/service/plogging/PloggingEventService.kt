package com.relogging.server.service.plogging

import com.relogging.server.dto.request.PloggingEventRequest
import com.relogging.server.dto.response.PloggingEventListResponse
import com.relogging.server.dto.response.PloggingEventResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

interface PloggingEventService {
    fun getPloggingEvent(id: Long): PloggingEventResponse

    fun getPloggingEventList(pageable: Pageable): Page<PloggingEventListResponse>

    fun createPloggingEvent(
        request: PloggingEventRequest,
        image: MultipartFile?,
    ): PloggingEventResponse

    fun deletePloggingEvent(id: Long)
}
