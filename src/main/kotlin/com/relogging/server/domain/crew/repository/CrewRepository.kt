package com.relogging.server.domain.crew.repository

import com.relogging.server.domain.crew.entity.Crew
import org.springframework.data.jpa.repository.JpaRepository

interface CrewRepository : JpaRepository<Crew, Long>
