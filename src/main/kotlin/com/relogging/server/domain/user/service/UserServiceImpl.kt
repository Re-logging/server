package com.relogging.server.domain.user.service

import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.domain.user.entity.User
import com.relogging.server.domain.user.repository.UserRepository
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    @Transactional(readOnly = true)
    override fun findUserByEmail(email: String): User? {
        return this.userRepository.findByEmail(email).orElse(null)
    }

    @Transactional(readOnly = true)
    override fun getUserById(id: Long): User {
        return this.userRepository.findById(id).orElseThrow {
            GlobalException(GlobalErrorCode.USER_NOT_FOUND)
        }
    }

    @Transactional
    override fun createUserWithEssentialInfo(
        name: String,
        email: String,
        nickName: String,
        socialType: SocialType,
        providerId: String,
    ): User {
        return this.userRepository.save(
            User(
                name = name,
                email = email,
                nickname = nickName,
                socialType = socialType,
                providerId = providerId,
            ),
        )
    }

    override fun getUser(id: Long): User =
        userRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.USER_NOT_FOUND)
        }
}
