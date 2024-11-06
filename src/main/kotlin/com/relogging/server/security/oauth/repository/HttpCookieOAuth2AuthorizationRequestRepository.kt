package com.relogging.server.security.oauth.repository

import com.relogging.server.security.util.CookieUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component

@Component
class HttpCookieOAuth2AuthorizationRequestRepository :
    AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    companion object {
        const val OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request"
        const val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"
        private const val COOKIE_EXPIRE_SECONDS = 600
    }

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            ?.let { cookie ->
                CookieUtils.deserialize(
                    cookie,
                    OAuth2AuthorizationRequest::class.java,
                )
            }
    }

    override fun removeAuthorizationRequest(
        request: HttpServletRequest,
        response: HttpServletResponse?,
    ): OAuth2AuthorizationRequest? {
        val authRequest = loadAuthorizationRequest(request)
        if (response != null) {
            this.removeAuthorizationRequestCookies(request, response)
        }
        return authRequest
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response)
            return
        }

        CookieUtils.addCookie(
            response,
            OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            CookieUtils.serialize(authorizationRequest),
            COOKIE_EXPIRE_SECONDS,
        )
        val redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)
        if (!redirectUriAfterLogin.isNullOrBlank()) {
            CookieUtils.addCookie(
                response,
                REDIRECT_URI_PARAM_COOKIE_NAME,
                redirectUriAfterLogin,
                COOKIE_EXPIRE_SECONDS,
            )
        }
    }

    fun removeAuthorizationRequestCookies(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
    }
}
