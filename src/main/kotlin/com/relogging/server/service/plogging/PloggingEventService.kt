package com.relogging.server.service.plogging

import com.relogging.server.dto.response.PloggingEventListResponse
import com.relogging.server.dto.response.PloggingEventResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PloggingEventService {
    fun getPloggingEvent(id: Long): PloggingEventResponse

    fun getPloggingEventList(pageable: Pageable): Page<PloggingEventListResponse>
}
