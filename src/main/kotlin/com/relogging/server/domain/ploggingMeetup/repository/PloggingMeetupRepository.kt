package com.relogging.server.domain.ploggingMeetup.repository

import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PloggingMeetupRepository : JpaRepository<PloggingMeetup, Long>, PloggingMeetupCustomRepository {
    fun findFirstByIdGreaterThanOrderByIdAsc(id: Long): Optional<PloggingMeetup>

    fun findFirstByIdLessThanOrderByIdDesc(id: Long): Optional<PloggingMeetup>
}
