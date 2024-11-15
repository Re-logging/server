package com.relogging.server.domain.user.service

import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.domain.user.entity.User
import org.springframework.web.multipart.MultipartFile

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

    fun updateAccountInfo(
        id: Long,
        name: String,
    ): User

    fun updateProfileInfo(
        id: Long,
        nickname: String,
        image: MultipartFile?,
    ): User
}
