package com.relogging.server.domain.user.repository

import com.relogging.server.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
