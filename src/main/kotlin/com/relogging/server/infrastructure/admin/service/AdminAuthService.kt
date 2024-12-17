package com.relogging.server.infrastructure.admin.service

import com.relogging.server.infrastructure.admin.dto.AdminRequest

interface AdminAuthService {
    fun kakaoLogin(request: AdminRequest): String
}
