package com.relogging.server.redis.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash("refresh_token")
class RefreshToken(
    @Id
    val userId: Long,
    val refreshToken: String,
    @TimeToLive
    val expiration: Long,
)
