package com.relogging.server.infrastructure.admin.service

import com.relogging.server.infrastructure.admin.dto.AdminRequest
import com.relogging.server.infrastructure.admin.entity.Admin

interface AdminAuthService {
    fun kakaoLogin(request: AdminRequest): String

    fun kakaoTokenRefresh(admin: Admin)
}
