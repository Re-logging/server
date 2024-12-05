package com.relogging.server.domain.user.controller

import com.relogging.server.domain.user.dto.UpdateAccountRequest
import com.relogging.server.domain.user.dto.UpdateProfileRequest
import com.relogging.server.domain.user.dto.UserConverter
import com.relogging.server.domain.user.dto.UserResponse
import com.relogging.server.domain.user.service.UserService
import com.relogging.server.security.details.PrincipalDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "유저 관련 API")
class UserController(
    private val userService: UserService,
) {
    @Operation(summary = "내 유저 정보 가져오기")
    @GetMapping("/my-info")
    fun getMyUserInfo(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<UserResponse> {
        val user = this.userService.getUserById(principalDetails.user.id!!)

        return ResponseEntity.ok(UserConverter.toResponse(user))
    }

    @Operation(summary = "내 계정 정보 수정하기")
    @PutMapping("/account")
    fun updateMyAccountInfo(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody request: @Valid UpdateAccountRequest,
    ): ResponseEntity<UserResponse> {
        val user = this.userService.updateAccountInfo(principalDetails.user.id!!, request.name)

        return ResponseEntity.ok(UserConverter.toResponse(user))
    }

    @Operation(summary = "내 프로필 정보 수정하기")
    @PutMapping("/profile", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMyProfileInfo(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestPart request: @Valid UpdateProfileRequest,
        @RequestPart(value = "image", required = false) image: MultipartFile?,
    ): ResponseEntity<UserResponse> {
        val user =
            this.userService.updateProfileInfo(
                principalDetails.user.id!!,
                request.nickname,
                image,
            )

        return ResponseEntity.ok(UserConverter.toResponse(user))
    }

    @Operation(summary = "계정 탈퇴하기")
    @DeleteMapping("/withdrawal")
    fun withdrawal(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ): ResponseEntity<Unit> {
        this.userService.deleteUser(principalDetails.user.id!!)
        return ResponseEntity.ok().build()
    }
}
