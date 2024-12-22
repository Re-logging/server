package com.relogging.server.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.relogging.server.global.exception.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?,
    ) {
        response?.apply {
            status = HttpStatus.UNAUTHORIZED.value()
            contentType = MediaType.APPLICATION_JSON_VALUE
            characterEncoding = "UTF-8"
            writer.write(
                objectMapper.writeValueAsString(
                    ErrorResponse(
                        status = HttpStatus.UNAUTHORIZED.value(),
                        error = "AUTH-001",
                        message = "인증이 필요합니다",
                    ),
                ),
            )
        }
    }
}
