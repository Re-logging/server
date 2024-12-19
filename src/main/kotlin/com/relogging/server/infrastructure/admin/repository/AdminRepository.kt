package com.relogging.server.infrastructure.admin.repository

import com.relogging.server.infrastructure.admin.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
    fun findByKakaoId(kakaoId: Long): Admin?
}
