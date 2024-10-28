package com.relogging.server.domain.user.service

import com.relogging.server.domain.user.entity.User

interface UserService {
    fun getUser(id: Long): User
}
