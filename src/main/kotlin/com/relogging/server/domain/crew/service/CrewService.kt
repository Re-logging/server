package com.relogging.server.domain.crew.service

import com.relogging.server.domain.crew.dto.CrewCreateRequest
import com.relogging.server.domain.user.entity.User
import org.springframework.web.multipart.MultipartFile

interface CrewService {
    fun createCrew(
        request: CrewCreateRequest,
        imageList: List<MultipartFile>,
        user: User,
    ): Long
}
