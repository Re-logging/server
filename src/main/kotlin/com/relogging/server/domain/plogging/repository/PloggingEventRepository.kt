package com.relogging.server.domain.plogging.repository

import com.relogging.server.domain.plogging.entity.PloggingEvent
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PloggingEventRepository : JpaRepository<PloggingEvent, Long> {
    fun findFirstByIdGreaterThanOrderByIdAsc(id: Long): Optional<PloggingEvent>

    fun findFirstByIdLessThanOrderByIdDesc(id: Long): Optional<PloggingEvent>
}
