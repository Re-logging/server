package com.relogging.server.domain.crew.controller

import com.relogging.server.domain.crew.dto.CrewCreateRequest
import com.relogging.server.domain.crew.service.CrewService
import com.relogging.server.security.details.PrincipalDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/crews")
@Tag(name = "Crew", description = "크루 관련 API")
class CrewController(
    private val crewService: CrewService,
) {
    @Operation(summary = "크루 생성하기")
    @PostMapping("/", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createCrew(
        @Valid @RequestPart request: CrewCreateRequest,
        @RequestPart(value = "image", required = false) imageList: List<MultipartFile> = emptyList(),
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<Long> {
        val id = crewService.createCrew(request, imageList, principalDetails.user)
        return ResponseEntity.ok(id)
    }
}
