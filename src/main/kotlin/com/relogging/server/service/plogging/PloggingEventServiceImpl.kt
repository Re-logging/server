package com.relogging.server.service.plogging

import com.relogging.server.convertor.ImageConvertor
import com.relogging.server.convertor.PloggingEventConvertor
import com.relogging.server.dto.request.PloggingEventRequest
import com.relogging.server.dto.response.PloggingEventListResponse
import com.relogging.server.dto.response.PloggingEventResponse
import com.relogging.server.entity.PloggingEvent
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.repository.PloggingEventRepository
import com.relogging.server.service.image.ImageService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PloggingEventServiceImpl(
    private val ploggingEventRepository: PloggingEventRepository,
    private val imageService: ImageService,
) : PloggingEventService {
    @Transactional(readOnly = true)
    override fun getPloggingEvent(id: Long): PloggingEventResponse = PloggingEventConvertor.toResponse(this.getPloggingEventById(id))

    @Transactional(readOnly = true)
    override fun getPloggingEventList(pageable: Pageable): Page<PloggingEventListResponse> {
        val findEvents: Page<PloggingEvent> = this.ploggingEventRepository.findAll(pageable)
        return findEvents.map { entity -> PloggingEventConvertor.toListResponse(entity) }
    }

    @Transactional
    override fun createPloggingEvent(
        request: PloggingEventRequest,
        image: MultipartFile?,
    ): PloggingEventResponse {
        val ploggingEvent: PloggingEvent = PloggingEventConvertor.toEntity(request)
        if (image != null) {
            val savedFilePath = this.imageService.saveImageFile(image)
            ploggingEvent.imageList +=
                ImageConvertor.toEntityWithPloggingEvent(
                    savedFilePath,
                    request.imageCaption,
                    ploggingEvent,
                )
        }
        val savedEvent = this.ploggingEventRepository.save(ploggingEvent)
        return PloggingEventConvertor.toResponse(savedEvent)
    }

    @Transactional
    override fun deletePloggingEvent(id: Long) = this.ploggingEventRepository.delete(this.getPloggingEventById(id))

    private fun getPloggingEventById(id: Long): PloggingEvent =
        this.ploggingEventRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_NOT_FOUND)
        }
}
