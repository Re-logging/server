package com.relogging.server.domain.crew.service

import com.relogging.server.domain.crew.dto.CrewCreateRequest
import com.sun.security.auth.UserPrincipal
import org.springframework.web.multipart.MultipartFile

interface CrewService {
    fun createCrew(
        request: CrewCreateRequest,
        imageList: List<MultipartFile>,
        userPrincipal: UserPrincipal,
    ): Long
}
