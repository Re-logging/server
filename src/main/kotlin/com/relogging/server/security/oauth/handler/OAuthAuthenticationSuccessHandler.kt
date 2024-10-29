package com.relogging.server.security.oauth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuthAuthenticationSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
//        super.onAuthenticationSuccess(request, response, authentication)
        println("OAuth Success")

        redirectStrategy.sendRedirect(request, response, "http://localhost:8080/oauth/success")
    }
}
