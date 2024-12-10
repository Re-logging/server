package com.relogging.server.domain.comment.repository

import com.relogging.server.domain.comment.entity.Report
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository : JpaRepository<Report, Long>
