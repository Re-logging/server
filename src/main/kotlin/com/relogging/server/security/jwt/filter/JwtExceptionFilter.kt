package com.relogging.server.security.jwt.filter

import com.relogging.server.global.exception.GlobalException
import com.relogging.server.global.exception.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class JwtExceptionFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: JwtException) {
            response.sendError(e.jwtErrorCode.status.value(), e.message)
        } catch (e: GlobalException) {
            response.sendError(e.globalErrorCode.status.value(), e.message)
        }
    }
}
