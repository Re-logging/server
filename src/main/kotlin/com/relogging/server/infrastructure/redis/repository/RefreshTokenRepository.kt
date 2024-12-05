package com.relogging.server.infrastructure.redis.repository

import com.relogging.server.infrastructure.redis.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshToken, Long>
