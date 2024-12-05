package com.relogging.server.domain.plogging.repository

import com.relogging.server.domain.plogging.entity.PloggingEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.Optional

interface PloggingEventRepository : JpaRepository<PloggingEvent, Long> {
    fun findFirstByIdGreaterThanOrderByIdAsc(id: Long): Optional<PloggingEvent>

    fun findFirstByIdLessThanOrderByIdDesc(id: Long): Optional<PloggingEvent>

    @Query("SELECT p.programNumber FROM PloggingEvent p")
    fun findAllProgramNumber(): List<String>

    fun deleteAllByNoticeEndDateBefore(today: LocalDate)

    @Modifying
    @Query("update PloggingEvent p set p.hits = p.hits + 1 where p.id = :id")
    fun increasingHits(
        @Param("id") id: Long?,
    ): Int
}
