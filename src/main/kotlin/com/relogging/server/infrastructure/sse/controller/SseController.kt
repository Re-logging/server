package com.relogging.server.infrastructure.sse.controller

import com.relogging.server.infrastructure.sse.service.SseService
import com.relogging.server.security.details.PrincipalDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/sse")
@Tag(name = "SSE")
class SseController(
    private val sseService: SseService,
) {
    @Operation(summary = "SSE 연결하기")
    @GetMapping("/connect", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<SseEmitter> {
        return ResponseEntity.ok(this.sseService.connect(principalDetails.user.id!!))
    }
}
