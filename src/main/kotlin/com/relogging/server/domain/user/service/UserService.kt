package com.relogging.server.domain.user.service

import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.domain.user.entity.User

interface UserService {
    fun findUserByEmail(email: String): User?

    fun getUserById(id: Long): User

    fun createUserWithEssentialInfo(
        name: String,
        email: String,
        nickName: String,
        socialType: SocialType,
        providerId: String,
    ): User

    fun getUser(id: Long): User
}
