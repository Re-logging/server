package com.relogging.server.domain.plogging.service

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.image.dto.ImageConverter
import com.relogging.server.domain.plogging.dto.PloggingEventConverter
import com.relogging.server.domain.plogging.dto.PloggingEventListResponse
import com.relogging.server.domain.plogging.dto.PloggingEventRequest
import com.relogging.server.domain.plogging.dto.PloggingEventResponse
import com.relogging.server.domain.plogging.dto.VolunteeringDetailApiResponse
import com.relogging.server.domain.plogging.dto.VolunteeringDetailApiResponseItem
import com.relogging.server.domain.plogging.dto.VolunteeringListApiResponse
import com.relogging.server.domain.plogging.dto.VolunteeringListApiResponseItem
import com.relogging.server.domain.plogging.entity.PloggingEvent
import com.relogging.server.domain.plogging.repository.PloggingEventRepository
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.infrastructure.aws.s3.AmazonS3Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    @Transactional
    override fun fetchPloggingEventList(keyword: String): Mono<Void> {
        val oneYearAgoStart = this.getOneYearAgoStart()
        val oneYearLaterEnd = this.getOneYearLaterEnd()

        return this.webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("http")
                    .host(API_HOST)
                    .path(LIST_API_PATH)
                    .queryParam("serviceKey", apiKey)
                    .queryParam("progrmBgnde", oneYearAgoStart)
                    .queryParam("progrmEndde", oneYearLaterEnd)
                    .queryParam("adultPosblAt", "Y")
                    .queryParam("yngbgsPosblAt", "Y")
                    .queryParam("numOfRows", "100")
                    .queryParam("pageNo", "1")
                    .queryParam("keyword", keyword)
                    .queryParam("schCateGu", "all")
                    .queryParam("actBeginTm", "00")
                    .queryParam("actEndTm", "24")
                    .queryParam("noticeBgnde", oneYearAgoStart)
                    .queryParam("noticeEndde", oneYearLaterEnd)
                    .build()
            }
            .retrieve()
            .bodyToMono(VolunteeringListApiResponse::class.java)
            .flatMap { apiResponse ->
                if (apiResponse.body!!.totalCount!! > 0) {
                    println(apiResponse.body.totalCount)
                    apiResponse.body.items!!.item!!.map { item ->
                        println(item)
                    }
                    // 블로킹 작업을 별도의 스레드에서 실행
                    Mono.fromCallable {
                        this.saveFetchedPloggingEventList(apiResponse.body.items.item!!)
                    }
                        .subscribeOn(Schedulers.boundedElastic()) // 블로킹 작업 전용 스레드 풀
                } else {
                    Mono.empty()
                }
            }
            .then()
            .onErrorResume {
                throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_FETCH_ERROR)
            }

//        response.subscribe { apiResponse ->
//            if (apiResponse.body!!.totalCount!! > 0) {
//                println(apiResponse.body.totalCount)
//                apiResponse.body.items!!.item!!.map { item ->
//                    println(item)
//                }
//                this.saveFetchedPloggingEventList(apiResponse.body.items.item!!)
//            }
//        }
    }

    override fun fetchPloggingEvent(programNumber: String): Mono<VolunteeringDetailApiResponse> {
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
            .bodyToMono(VolunteeringDetailApiResponse::class.java)
            .onErrorResume {
                throw GlobalException(GlobalErrorCode.PLOGGING_EVENT_FETCH_ERROR)
            }
    }

    @Transactional
    override fun saveFetchedPloggingEventList(itemList: List<VolunteeringListApiResponseItem>) {
        val savedNumberList = this.ploggingEventRepository.findAllProgramNumber()
        val newItemList =
            itemList.filterNot {
                it.programRegistrationNumber in savedNumberList
            }

        newItemList.forEach { item ->
            this.fetchPloggingEvent(item.programRegistrationNumber!!).subscribe { res ->
                if (res.body!!.totalCount == 1) {
                    this.saveFetchedPloggingEvent(res.body.items!!.item!![0], item.url!!)
                }
            }
        }
    }

    @Transactional
    override fun saveFetchedPloggingEvent(
        item: VolunteeringDetailApiResponseItem,
        url: String,
    ): PloggingEvent {
        val ploggingEvent = PloggingEventConverter.toEntity(item, url)
        return this.ploggingEventRepository.save(ploggingEvent)
    }

    @Transactional
    @Scheduled(cron = "0 30 3 * * *") // 매일 오전 3시 30분에 작업 수행
    override fun fetchAndSavePloggingEvent() {
        this.fetchPloggingEventList("플로깅")
            .then(this.fetchPloggingEventList("줍깅"))
            .subscribe()
//        this.fetchPloggingEventList("플로깅").subscribe { apiResponse ->
//            if (apiResponse.body!!.totalCount!! > 0) {
//                println(apiResponse.body.totalCount)
//                apiResponse.body.items!!.item!!.map { item ->
//                    println(item)
//                }
//                this.saveFetchedPloggingEventList(apiResponse.body.items.item!!)
//            }
//        }
//
//        this.fetchPloggingEventList("줍깅").subscribe { apiResponse ->
//            if (apiResponse.body!!.totalCount!! > 0) {
//                println(apiResponse.body.totalCount)
//                apiResponse.body.items!!.item!!.map { item ->
//                    println(item)
//                }
//                this.saveFetchedPloggingEventList(apiResponse.body.items.item!!)
//            }
//        }
    }

    private fun getOneYearAgoStart(): String {
        val currentYear = LocalDate.now().year

        val oneYearAgoStart = LocalDate.of(currentYear - 1, 1, 1)

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

        return oneYearAgoStart.format(formatter)
    }

    private fun getOneYearLaterEnd(): String {
        val currentYear = LocalDate.now().year

        val oneYearLaterEnd = LocalDate.of(currentYear + 1, 12, 31)

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

        return oneYearLaterEnd.format(formatter)
    }
}
