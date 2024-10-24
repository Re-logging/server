package com.relogging.server.domain.plogging.service

import com.relogging.server.domain.image.dto.ImageConvertor
import com.relogging.server.domain.image.service.ImageService
import com.relogging.server.domain.plogging.dto.PloggingEventConvertor
import com.relogging.server.domain.plogging.dto.PloggingEventListResponse
import com.relogging.server.domain.plogging.dto.PloggingEventRequest
import com.relogging.server.domain.plogging.dto.PloggingEventResponse
import com.relogging.server.domain.plogging.entity.PloggingEvent
import com.relogging.server.domain.plogging.repository.PloggingEventRepository
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PloggingEventServiceImpl(
    private val ploggingEventRepository: PloggingEventRepository,
    private val imageService: ImageService,
    @Value("\${image-dir.plogging-event}")
    private var imageUploadDir: String,
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
            val savedFilePath = this.imageService.saveImageFile(image, imageUploadDir)
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

    @Transactional(readOnly = true)
    override fun getNextPloggingEvent(currentId: Long): PloggingEventResponse =
        PloggingEventConvertor.toResponse(
            this.ploggingEventRepository.findFirstByIdGreaterThanOrderByIdAsc(currentId)
                .orElseThrow {
                    throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_NOT_FOUND)
                },
        )

    @Transactional(readOnly = true)
    override fun getPrevPloggingEvent(currentId: Long): PloggingEventResponse =
        PloggingEventConvertor.toResponse(
            this.ploggingEventRepository.findFirstByIdLessThanOrderByIdDesc(currentId)
                .orElseThrow {
                    throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_NOT_FOUND)
                },
        )

    private fun getPloggingEventById(id: Long): PloggingEvent =
        this.ploggingEventRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_NOT_FOUND)
        }
}
