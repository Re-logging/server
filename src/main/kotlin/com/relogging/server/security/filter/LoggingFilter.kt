package com.relogging.server.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class LoggingFilter : OncePerRequestFilter() {
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // 특정 경로에 대해서만 필터 작동
        return !request.requestURI.contains("/login/oauth2/code/google")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        // 요청이 들어올 때 로그 출력
        println("OAuth2 callback request received at /login/oauth2/code/google")

        // 필터 체인에 요청 전달
        filterChain.doFilter(request, response)
    }
}
