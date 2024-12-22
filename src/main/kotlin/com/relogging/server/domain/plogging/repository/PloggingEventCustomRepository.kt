package com.relogging.server.domain.plogging.repository

import com.relogging.server.domain.plogging.PloggingEventSortType
import com.relogging.server.domain.plogging.entity.PloggingEvent
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface PloggingEventCustomRepository {
    fun findPloggingEvents(
        region: String?,
        isOpen: Boolean?,
        pageable: Pageable,
        sortBy: PloggingEventSortType?,
        sortDirection: Sort.Direction,
    ): Page<PloggingEvent>
}
