package com.relogging.server.domain.user.controller

import com.relogging.server.domain.user.dto.UserConverter
import com.relogging.server.domain.user.dto.UserResponse
import com.relogging.server.domain.user.service.UserService
import com.relogging.server.security.details.PrincipalDetails
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "내 유저 정보 가져오기")
    @GetMapping("/my-info")
    fun getMyAccountInfo(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<UserResponse> {
        val user = this.userService.getUserById(principalDetails.user.id!!)

        return ResponseEntity.ok(UserConverter.toResponse(user))
    }
}
