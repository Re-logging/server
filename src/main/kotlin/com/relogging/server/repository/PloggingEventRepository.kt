package com.relogging.server.repository

import com.relogging.server.entity.PloggingEvent
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PloggingEventRepository : JpaRepository<PloggingEvent, Long> {
    fun findFirstByIdGreaterThanOrderByIdAsc(id: Long): Optional<PloggingEvent>

    fun findFirstByIdLessThanOrderByIdDesc(id: Long): Optional<PloggingEvent>
}
