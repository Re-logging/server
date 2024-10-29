package com.relogging.server.global.security.jwt.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtServiceImpl(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.validity-in-seconds")
    private val accessExpirationTime: String,
    @Value("\${jwt.validity-in-seconds-refresh")
    private val refreshExpirationTime: String,
) : JwtService
