package com.relogging.server.domain.plogging.service

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.image.dto.ImageConverter
import com.relogging.server.domain.plogging.dto.PloggingEventConverter
import com.relogging.server.domain.plogging.dto.PloggingEventListResponse
import com.relogging.server.domain.plogging.dto.PloggingEventRequest
import com.relogging.server.domain.plogging.dto.PloggingEventResponse
import com.relogging.server.domain.plogging.entity.PloggingEvent
import com.relogging.server.domain.plogging.repository.PloggingEventRepository
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.infrastructure.aws.s3.AmazonS3Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PloggingEventServiceImpl(
    private val ploggingEventRepository: PloggingEventRepository,
    private val amazonS3Service: AmazonS3Service,
    @Value("\${cloud.aws.s3.path.plogging-event}")
    private var imageUploadDir: String,
) : PloggingEventService {
    @Transactional(readOnly = true)
    override fun getPloggingEvent(id: Long): PloggingEventResponse {
        val event = this.getPloggingEventById(id)
        return PloggingEventConverter.toResponse(event, getRootComments(event))
    }

    @Transactional
    override fun getPloggingEventEntity(id: Long): PloggingEvent = this.getPloggingEventById(id)

    @Transactional(readOnly = true)
    override fun getPloggingEventList(pageable: Pageable): Page<PloggingEventListResponse> {
        val findEvents: Page<PloggingEvent> = this.ploggingEventRepository.findAll(pageable)
        return findEvents.map { entity -> PloggingEventConverter.toListResponse(entity) }
    }

    @Transactional
    override fun createPloggingEvent(
        request: PloggingEventRequest,
        image: MultipartFile?,
    ): PloggingEventResponse {
        val ploggingEvent: PloggingEvent = PloggingEventConverter.toEntity(request)
        if (image != null) {
            val savedFilePath = this.amazonS3Service.uploadFile(image, imageUploadDir)
            ploggingEvent.imageList +=
                ImageConverter.toEntityWithPloggingEvent(
                    savedFilePath,
                    request.imageCaption,
                    ploggingEvent,
                )
        }
        val savedEvent = this.ploggingEventRepository.save(ploggingEvent)
        return PloggingEventConverter.toResponse(savedEvent, getRootComments(savedEvent))
    }

    @Transactional
    override fun deletePloggingEvent(id: Long) = this.ploggingEventRepository.delete(this.getPloggingEventById(id))

    @Transactional(readOnly = true)
    override fun getNextPloggingEvent(currentId: Long): PloggingEventResponse {
        val nextEvent =
            ploggingEventRepository.findFirstByIdGreaterThanOrderByIdAsc(currentId)
                .orElseThrow { throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_NOT_FOUND) }
        return PloggingEventConverter.toResponse(
            nextEvent,
            getRootComments(nextEvent),
        )
    }

    @Transactional(readOnly = true)
    override fun getPrevPloggingEvent(currentId: Long): PloggingEventResponse {
        val prevEvent =
            ploggingEventRepository.findFirstByIdLessThanOrderByIdDesc(currentId)
                .orElseThrow {
                    throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_NOT_FOUND)
                }
        return PloggingEventConverter.toResponse(
            prevEvent,
            getRootComments(prevEvent),
        )
    }

    private fun getPloggingEventById(id: Long): PloggingEvent =
        this.ploggingEventRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_NOT_FOUND)
        }

    private fun getRootComments(event: PloggingEvent): List<Comment> =
        event.commentList
            .filter { it.parentComment == null }
            .sortedByDescending { it.createAt }
}
