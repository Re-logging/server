package com.relogging.server.infrastructure.admin.service

import com.relogging.server.infrastructure.admin.dto.AdminRequest
import com.relogging.server.infrastructure.admin.entity.Admin

interface AdminService {
    fun adminKakaoLogin(request: AdminRequest): String

    fun findById(id: Long): Admin

    fun findAll(): List<Admin>
}
