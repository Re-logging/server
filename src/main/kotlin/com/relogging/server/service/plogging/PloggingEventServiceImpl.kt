package com.relogging.server.service.plogging

import com.relogging.server.convertor.PloggingEventConvertor
import com.relogging.server.dto.response.PloggingEventListResponse
import com.relogging.server.dto.response.PloggingEventResponse
import com.relogging.server.entity.PloggingEvent
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.repository.PloggingEventRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PloggingEventServiceImpl(
    private val ploggingEventRepository: PloggingEventRepository,
) : PloggingEventService {
    @Transactional(readOnly = true)
    override fun getPloggingEvent(id: Long): PloggingEventResponse {
        val findEvent: PloggingEvent =
            this.ploggingEventRepository.findById(id).orElseThrow {
                throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_NOT_FOUND)
            }
        return PloggingEventConvertor.toResponse(findEvent)
    }

    @Transactional(readOnly = true)
    override fun getPloggingEventList(pageable: Pageable): Page<PloggingEventListResponse> {
        val findEvents: Page<PloggingEvent> = this.ploggingEventRepository.findAll(pageable)
        return findEvents.map { entity -> PloggingEventConvertor.toListResponse(entity) }
    }
}
