package com.relogging.server.security.service

interface AuthService {
    fun reissue(refreshToken: String): String
}
