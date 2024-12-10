package com.relogging.server.domain.ploggingMeetup.repository

import com.relogging.server.domain.ploggingMeetup.PloggingMeetupSortType
import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface PloggingMeetupCustomRepository {
    fun findPloggingMeetups(
        region: String?,
        isClosed: Boolean?,
        pageable: Pageable,
        sortBy: PloggingMeetupSortType?,
        sortDirection: Sort.Direction,
    ): Page<PloggingMeetup>
}
