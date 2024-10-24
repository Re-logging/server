package com.relogging.server.domain.plogging.controller

import com.relogging.server.domain.plogging.dto.PloggingEventListResponse
import com.relogging.server.domain.plogging.dto.PloggingEventResponse
import com.relogging.server.domain.plogging.service.PloggingEventService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ploggingEvent")
@Tag(name = "Plogging Event", description = "플로깅 행사 관련 API")
class PloggingEventController(
    private val ploggingEventService: PloggingEventService,
) {
    @Operation(summary = "플로깅 행사 리스트 조회하기")
    @GetMapping("/list")
    fun getPloggingEventList(pageable: Pageable): ResponseEntity<Page<PloggingEventListResponse>> {
        val response: Page<PloggingEventListResponse> =
            this.ploggingEventService.getPloggingEventList(pageable)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "플로깅 행사 조회하기")
    @GetMapping("/{id}")
    fun getPloggingEvent(
        @PathVariable id: Long,
    ): ResponseEntity<PloggingEventResponse> {
        val response: PloggingEventResponse = this.ploggingEventService.getPloggingEvent(id)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "다음 플로깅 행사 조회하기")
    @GetMapping("/{currentId}/next")
    fun getNextPloggingEvent(
        @PathVariable currentId: Long,
    ): ResponseEntity<PloggingEventResponse> {
        val response: PloggingEventResponse =
            this.ploggingEventService.getNextPloggingEvent(currentId)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "이전 플로깅 행사 조회하기")
    @GetMapping("/{currentId}/prev")
    fun getPrevPloggingEvent(
        @PathVariable currentId: Long,
    ): ResponseEntity<PloggingEventResponse> {
        val response: PloggingEventResponse =
            this.ploggingEventService.getPrevPloggingEvent(currentId)
        return ResponseEntity.ok(response)
    }
}
