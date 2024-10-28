package com.relogging.server.domain.crew.dto

import com.relogging.server.domain.crew.entity.MeetingFrequency
import com.relogging.server.domain.crew.entity.RecruitmentType
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CrewRequest(
    val id: Long,
)

data class CrewCreateRequest(
    @field:NotBlank(message = "크루이름을 입력해 주세요.")
    val crewTitle: String,
    @field:NotBlank(message = "크루소개 및 활동목적을 입력해 주세요.")
    @field:Size(max = 100, message = "크루소개는 100자 이내로 입력해 주세요.")
    val crewDescription: String,
    @field:NotBlank(message = "활동지역을 선택해 주세요.")
    val activityRegion: String,
    @field:NotNull(message = "빈도를 선택해 주세요.")
    val meetingFrequency: MeetingFrequency,
    @field:Min(value = 1, message = "횟수는 최소 1 이상이어야 합니다.")
    val meetingCount: Int?,
    @field:NotNull(message = "허용 인원을 입력해 주세요.")
    @field:Max(value = 1500, message = "허용 인원은 최대 1500명까지 설정할 수 있습니다.")
    val maxMembers: Int,
    @field:NotBlank(message = "소통방식(링크)을 입력해 주세요.")
    val contact: String,
    val specialNotes: String? = null,
    @field:NotNull(message = "모집방식을 선택해 주세요.")
    val recruitmentType: RecruitmentType,
)
