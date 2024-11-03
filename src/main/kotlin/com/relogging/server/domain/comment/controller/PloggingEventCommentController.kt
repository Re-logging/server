package com.relogging.server.domain.comment.controller

import com.relogging.server.domain.comment.dto.CommentCreateRequest
import com.relogging.server.domain.comment.service.CommentService
import com.relogging.server.security.details.PrincipalDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ploggingEvents/{eventId}/comments")
@Tag(name = "Plogging Event Comment", description = "지자체 플로깅 댓글 관련 API")
class PloggingEventCommentController(
    private val commentService: CommentService,
) {
    @Operation(summary = "댓글 생성하기")
    @PostMapping
    fun createComment(
        @PathVariable eventId: Long,
        @RequestBody request: CommentCreateRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<Long> {
        val response = commentService.createComment(eventId, request, principalDetails.user)
        return ResponseEntity.ok(response)
    }
}
