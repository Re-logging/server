package com.relogging.server.domain.plogging.service

import com.relogging.server.domain.plogging.dto.PloggingEventListResponse
import com.relogging.server.domain.plogging.dto.PloggingEventRequest
import com.relogging.server.domain.plogging.dto.PloggingEventResponse
import com.relogging.server.domain.plogging.dto.VolunteeringDetailApiResponse
import com.relogging.server.domain.plogging.dto.VolunteeringListApiResponse
import com.relogging.server.domain.plogging.entity.PloggingEvent
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono

interface PloggingEventService {
    fun getPloggingEvent(id: Long): PloggingEventResponse

    fun getPloggingEventEntity(id: Long): PloggingEvent

    fun getPloggingEventList(pageable: Pageable): Page<PloggingEventListResponse>

    fun createPloggingEvent(
        request: PloggingEventRequest,
        image: MultipartFile?,
    ): PloggingEventResponse

    fun deletePloggingEvent(id: Long)

    fun getNextPloggingEvent(currentId: Long): PloggingEventResponse

    fun getPrevPloggingEvent(currentId: Long): PloggingEventResponse

    fun fetchPloggingEventList(): Mono<VolunteeringListApiResponse>

    fun fetchPloggingEvent(programNumber: String): Mono<VolunteeringDetailApiResponse>
}
