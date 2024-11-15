package com.relogging.server.domain.user.dto

import com.relogging.server.domain.user.entity.User

object UserConverter {
    fun toResponse(user: User) =
        UserResponse(
            name = user.name,
            email = user.email,
            nickname = user.nickname,
            image = user.profileImage,
        )
}
