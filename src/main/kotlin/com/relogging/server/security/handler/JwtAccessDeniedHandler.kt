package com.relogging.server.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.relogging.server.global.exception.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
) : AccessDeniedHandler {
    @Override
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?,
    ) {
        response?.apply {
            status = HttpStatus.FORBIDDEN.value()
            contentType = MediaType.APPLICATION_JSON_VALUE
            characterEncoding = "UTF-8"
            writer.write(
                objectMapper.writeValueAsString(
                    ErrorResponse(
                        status = HttpStatus.FORBIDDEN.value(),
                        error = "AUTH-002",
                        message = "접근 권한이 없습니다",
                    ),
                ),
            )
        }
    }
}
