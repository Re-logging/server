package com.relogging.server.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.relogging.server.global.exception.ErrorResponse
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class AuthenticationExceptionFilter(private val objectMapper: ObjectMapper) :
    OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: GlobalException) {
            setErrorResponse(response, e.globalErrorCode)
        }
    }

    private fun setErrorResponse(
        response: HttpServletResponse,
        errorCode: GlobalErrorCode,
    ) {
        response.status = errorCode.status.value()
        response.contentType = "application/json; charset=UTF-8"
        val errorResponse =
            ErrorResponse(
                status = errorCode.status.value(),
                error = errorCode.errorCode,
                message = errorCode.message,
            )
        try {
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
