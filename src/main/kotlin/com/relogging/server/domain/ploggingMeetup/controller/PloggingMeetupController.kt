package com.relogging.server.domain.ploggingMeetup.controller

import com.relogging.server.domain.ploggingMeetup.PloggingMeetupSortType
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupListResponse
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupRequest
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupResponse
import com.relogging.server.domain.ploggingMeetup.service.PloggingMeetupService
import com.relogging.server.security.details.PrincipalDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/ploggingMeetups")
@Tag(name = "Plogging Meetup", description = "플로깅 모임 관련 API")
class PloggingMeetupController(
    private val ploggingMeetupService: PloggingMeetupService,
) {
    @Operation(summary = "플로깅 모임 리스트 조회하기")
    @GetMapping("/list")
    fun getPloggingEventList(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "9", required = false) pageSize: Int,
        @RequestParam(required = false) region: String?,
        @RequestParam(required = false, defaultValue = "false") isOpen: Boolean,
        @RequestParam(required = false) sortBy: PloggingMeetupSortType?,
        @RequestParam(required = false, defaultValue = "DESC") sortDirection: Sort.Direction,
    ): ResponseEntity<PloggingMeetupListResponse> {
        val response =
            ploggingMeetupService.getMeetupList(
                page = page,
                pageSize = pageSize,
                region = region,
                isOpen = isOpen,
                sortBy = sortBy,
                sortDirection = sortDirection,
            )
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "플로깅 모임 생성하기")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPloggingMeetup(
        @RequestPart @Valid request: PloggingMeetupRequest,
        @RequestPart(value = "image", required = false) image: MultipartFile?,
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<Long> {
        val id = ploggingMeetupService.createMeetup(request, image, principalDetails.user)
        return ResponseEntity.ok(id)
    }

    @Operation(summary = "플로깅 모임 조회하기", description = "조회수를 증가시킵니다.")
    @GetMapping("/{id}")
    fun getPloggingMeetup(
        @PathVariable id: Long,
    ): ResponseEntity<PloggingMeetupResponse> {
        val response = ploggingMeetupService.getMeetup(id, true)
        return ResponseEntity.ok(response)
    }

//    @Operation(summary = "다음 플로깅 모임 조회하기", description = "조회수를 증가시킵니다.")
//    @GetMapping("/{currentId}/next")
//    fun getNextPloggingMeetup(
//        @PathVariable currentId: Long,
//    ): ResponseEntity<PloggingMeetupResponse> {
//        val response: PloggingMeetupResponse =
//            ploggingMeetupService.getNextMeetup(currentId)
//        return ResponseEntity.ok(response)
//    }
//
//    @Operation(summary = "이전 플로깅 모임 조회하기", description = "조회수를 증가시킵니다.")
//    @GetMapping("/{currentId}/prev")
//    fun getPrevPloggingMeetup(
//        @PathVariable currentId: Long,
//    ): ResponseEntity<PloggingMeetupResponse> {
//        val response: PloggingMeetupResponse =
//            ploggingMeetupService.getPrevMeetup(currentId)
//        return ResponseEntity.ok(response)
//    }
}
