package com.relogging.server.domain.plogging.service

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.image.dto.ImageConverter
import com.relogging.server.domain.plogging.dto.PloggingEventConverter
import com.relogging.server.domain.plogging.dto.PloggingEventListResponse
import com.relogging.server.domain.plogging.dto.PloggingEventRequest
import com.relogging.server.domain.plogging.dto.PloggingEventResponse
import com.relogging.server.domain.plogging.dto.VolunteeringApiResponse
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
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class PloggingEventServiceImpl(
    private val ploggingEventRepository: PloggingEventRepository,
    private val amazonS3Service: AmazonS3Service,
    @Value("\${cloud.aws.s3.path.plogging-event}")
    private var imageUploadDir: String,
    private val webClient: WebClient,
    @Value("\${1365-api.key}")
    private val apiKey: String,
) : PloggingEventService {
    companion object {
        private const val API_HOST = "openapi.1365.go.kr"
        private const val LIST_API_PATH =
            "openapi/service/rest/VolunteerPartcptnService/getVltrSearchWordList"
        private const val DETAIL_API_PATH =
            "openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem"
    }

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
    override fun deletePloggingEvent(id: Long) =
        this.ploggingEventRepository.delete(this.getPloggingEventById(id))

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

    override fun fetchPloggingEventList(): Mono<VolunteeringApiResponse> {
        return this.webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("http")
                    .host(API_HOST)
                    .path(LIST_API_PATH)
                    .queryParam("serviceKey", apiKey)
                    .queryParam("progrmBgnde", "20230101")
                    .queryParam("progrmEndde", "20251212")
                    .queryParam("adultPosblAt", "Y")
                    .queryParam("yngbgsPosblAt", "Y")
                    .queryParam("numOfRows", "100")
                    .queryParam("pageNo", "1")
                    .queryParam("keyword", "플로깅")
                    .queryParam("schCateGu", "all")
                    .queryParam("actBeginTm", "00")
                    .queryParam("actEndTm", "24")
                    .queryParam("noticeBgnde", "20230101")
                    .queryParam("noticeEndde", "20251212")
                    .build()
            }
            .retrieve()
            .bodyToMono(VolunteeringApiResponse::class.java)
            .onErrorResume {
                throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_FETCH_ERROR)
            }
    }

    override fun fetchPloggingEvent(programNumber: String): Mono<VolunteeringApiResponse> {
        return this.webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("http")
                    .host(API_HOST)
                    .path(DETAIL_API_PATH)
                    .queryParam("serviceKey", apiKey)
                    .queryParam("progrmRegistNo", programNumber)
                    .build()
            }
            .retrieve()
            .bodyToMono(VolunteeringApiResponse::class.java)
            .onErrorResume {
                throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_FETCH_ERROR)
            }
    }
}
