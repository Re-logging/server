package com.relogging.server.domain.ploggingMeetup.service

import com.relogging.server.domain.ploggingMeetup.PloggingMeetupSortType
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupCreateRequest
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupListResponse
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupResponse
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupUpdateRequest
import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup
import com.relogging.server.domain.user.entity.User
import org.springframework.data.domain.Sort
import org.springframework.web.multipart.MultipartFile

interface PloggingMeetupService {
    fun createMeetup(
        request: PloggingMeetupCreateRequest,
        image: MultipartFile?,
        user: User,
    ): Long

    fun getMeetup(
        id: Long,
        increaseHits: Boolean = false,
    ): PloggingMeetupResponse

    fun getNextMeetup(currentId: Long): PloggingMeetupResponse

    fun getPrevMeetup(currentId: Long): PloggingMeetupResponse

    fun getMeetupList(
        page: Int,
        pageSize: Int,
        region: String?,
        isOpen: Boolean?,
        sortBy: PloggingMeetupSortType?,
        sortDirection: Sort.Direction,
    ): PloggingMeetupListResponse

    fun getMeetupEntity(id: Long): PloggingMeetup

    fun updateMeetup(
        id: Long,
        request: PloggingMeetupUpdateRequest,
        user: User,
    )

    fun deleteMeetup(
        id: Long,
        user: User,
    )
}
