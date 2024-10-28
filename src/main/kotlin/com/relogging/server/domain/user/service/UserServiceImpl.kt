package com.relogging.server.domain.user.service

import com.relogging.server.domain.user.entity.User
import com.relogging.server.domain.user.repository.UserRepository
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun getUser(id: Long): User =
        userRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.USER_NOT_FOUND)
        }
}
