package com.relogging.server.repository

import com.relogging.server.entity.PloggingEvent
import org.springframework.data.jpa.repository.JpaRepository

interface PloggingEventRepository : JpaRepository<PloggingEvent, Long>
