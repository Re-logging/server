package com.relogging.server.domain.comment.controller

import com.relogging.server.domain.comment.dto.ReportCommentRequest
import com.relogging.server.domain.comment.service.ReportCommentService
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
@RequestMapping("/api/comments/{commentId}/report")
@Tag(name = "Report Comment")
class ReportCommentController(
    private val reportCommentService: ReportCommentService,
) {
    @Operation(summary = "댓글 신고하기")
    @PostMapping
    fun reportComment(
        @PathVariable commentId: Long,
        @RequestBody request: ReportCommentRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<Long> {
        val response = reportCommentService.report(commentId, request, principalDetails.user)
        return ResponseEntity.ok(response)
    }
}
