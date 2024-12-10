package com.relogging.server.domain.plogging.controller

import com.relogging.server.domain.plogging.PloggingEventSortType
import com.relogging.server.domain.plogging.dto.PloggingEventListResponse
import com.relogging.server.domain.plogging.dto.PloggingEventResponse
import com.relogging.server.domain.plogging.service.PloggingEventService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ploggingEvents")
@Tag(name = "Plogging Event", description = "플로깅 행사 관련 API")
class PloggingEventController(
    private val ploggingEventService: PloggingEventService,
) {
    @Operation(summary = "플로깅 모임 리스트 조회하기")
    @GetMapping("/list")
    fun getPloggingEventList(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "9", required = false) pageSize: Int,
        @RequestParam(required = false) region: String?,
        @RequestParam(required = false, defaultValue = "false") isOpen: Boolean?,
        @RequestParam(required = false, defaultValue = "END_DATE") sortBy: PloggingEventSortType?,
        @RequestParam(required = false, defaultValue = "DESC") sortDirection: Sort.Direction,
    ): ResponseEntity<Page<PloggingEventListResponse>> {
        val response: Page<PloggingEventListResponse> =
            ploggingEventService.getPloggingEventList(
                page = page,
                pageSize = pageSize,
                region = region,
                isOpen = isOpen,
                sortBy = sortBy,
                sortDirection = sortDirection,
            )
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "플로깅 행사 조회하기")
    @GetMapping("/{id}")
    fun getPloggingEvent(
        @PathVariable id: Long,
    ): ResponseEntity<PloggingEventResponse> {
        val response: PloggingEventResponse = this.ploggingEventService.getPloggingEvent(id)
        this.ploggingEventService.increasingHits(response.id)
        return ResponseEntity.ok(response)
    }

//    @Operation(summary = "다음 플로깅 행사 조회하기")
//    @GetMapping("/{currentId}/next")
//    fun getNextPloggingEvent(
//        @PathVariable currentId: Long,
//    ): ResponseEntity<PloggingEventResponse> {
//        val response: PloggingEventResponse =
//            this.ploggingEventService.getNextPloggingEvent(currentId)
//        this.ploggingEventService.increasingHits(response.id)
//        return ResponseEntity.ok(response)
//    }
//
//    @Operation(summary = "이전 플로깅 행사 조회하기")
//    @GetMapping("/{currentId}/prev")
//    fun getPrevPloggingEvent(
//        @PathVariable currentId: Long,
//    ): ResponseEntity<PloggingEventResponse> {
//        val response: PloggingEventResponse =
//            this.ploggingEventService.getPrevPloggingEvent(currentId)
//        this.ploggingEventService.increasingHits(response.id)
//        return ResponseEntity.ok(response)
//    }
}
