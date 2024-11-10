package com.relogging.server.global.util

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.util.SerializationUtils
import java.util.Base64

object CookieUtils {
    fun getCookie(
        request: HttpServletRequest,
        name: String,
    ): Cookie? {
        val cookies = request.cookies
        if (cookies != null) {
            for (cookie in cookies) {
                if (cookie.name == name) {
                    return cookie
                }
            }
        }
        return null
    }

    fun addCookie(
        response: HttpServletResponse,
        name: String,
        value: String,
        maxAge: Int,
    ) {
        val cookie = Cookie(name, value)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = maxAge
        response.addCookie(cookie)
    }

    fun deleteCookie(
        request: HttpServletRequest,
        response: HttpServletResponse,
        name: String,
    ) {
        val cookies = request.cookies
        if (cookies != null) {
            for (cookie in cookies) {
                if (cookie.name == name) {
                    cookie.value = ""
                    cookie.path = "/"
                    cookie.maxAge = 0
                    response.addCookie(cookie)
                }
            }
        }
    }

    fun serialize(obj: Any): String {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj))
    }

    fun <T> deserialize(
        cookie: Cookie,
        cls: Class<T>,
    ): T {
        return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.value)))
    }

    fun createHttpOnlySecureCookie(
        name: String,
        value: String,
        maxAge: Int,
    ): Cookie {
        val cookie = Cookie(name, value)
        cookie.maxAge = maxAge
        cookie.isHttpOnly = true
        cookie.path = "/"
        cookie.secure = true

        return cookie
    }
}
