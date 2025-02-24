package com.relogging.server.domain.plogging.service

import com.relogging.server.domain.plogging.PloggingEventSortType
import com.relogging.server.domain.plogging.dto.PloggingEventListResponse
import com.relogging.server.domain.plogging.dto.PloggingEventRequest
import com.relogging.server.domain.plogging.dto.PloggingEventResponse
import com.relogging.server.domain.plogging.dto.VolunteeringDetailApiResponse
import com.relogging.server.domain.plogging.dto.VolunteeringDetailApiResponseItem
import com.relogging.server.domain.plogging.dto.VolunteeringListApiResponseItem
import com.relogging.server.domain.plogging.entity.PloggingEvent
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono

interface PloggingEventService {
    fun getPloggingEvent(id: Long): PloggingEventResponse

    fun getPloggingEventEntity(id: Long): PloggingEvent

    fun getPloggingEventList(
        page: Int,
        pageSize: Int,
        region: String?,
        isOpen: Boolean?,
        sortBy: PloggingEventSortType?,
        sortDirection: Sort.Direction,
    ): Page<PloggingEventListResponse>

    fun createPloggingEvent(
        request: PloggingEventRequest,
        image: MultipartFile?,
    ): PloggingEventResponse

    fun deletePloggingEvent(id: Long)

    fun getNextPloggingEvent(currentId: Long): PloggingEventResponse

    fun getPrevPloggingEvent(currentId: Long): PloggingEventResponse

    fun fetchPloggingEventList(keyword: String): Mono<Void>

    fun fetchPloggingEvent(programNumber: String): Mono<VolunteeringDetailApiResponse>

    fun saveFetchedPloggingEventList(itemList: List<VolunteeringListApiResponseItem>): Mono<Void>

    fun saveFetchedPloggingEvent(
        item: VolunteeringDetailApiResponseItem,
        url: String,
    ): Mono<PloggingEvent>

    fun deleteExpiredPloggingEvents()

    fun increasingHits(id: Long)
}
