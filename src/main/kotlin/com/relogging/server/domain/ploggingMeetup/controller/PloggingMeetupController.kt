package com.relogging.server.domain.ploggingMeetup.controller

import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupRequest
import com.relogging.server.domain.ploggingMeetup.service.PloggingMeetupService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ploggingMeetup")
@Tag(name = "Plogging Meetup", description = "플로깅 모임 관련 API")
class PloggingMeetupController(
    private val ploggingMeetupService: PloggingMeetupService,
) {
    @Operation(summary = "플로깅 모임 생성하기")
    @PostMapping("/", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPloggingMeetup(
        @ModelAttribute @Valid request: PloggingMeetupRequest,
    ): ResponseEntity<Long> {
        val id = ploggingMeetupService.createMeetup(request, request.image)
        return ResponseEntity.ok(id)
    }
}
