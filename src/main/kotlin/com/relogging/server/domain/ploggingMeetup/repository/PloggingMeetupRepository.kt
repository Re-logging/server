package com.relogging.server.domain.ploggingMeetup.repository

import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup
import org.springframework.data.jpa.repository.JpaRepository

interface PloggingMeetupRepository : JpaRepository<PloggingMeetup, Long>, PloggingMeetupCustomRepository
