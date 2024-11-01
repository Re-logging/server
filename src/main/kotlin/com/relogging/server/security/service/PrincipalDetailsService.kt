package com.relogging.server.security.service

import com.relogging.server.domain.user.entity.User
import com.relogging.server.domain.user.service.UserService
import com.relogging.server.security.details.PrincipalDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class PrincipalDetailsService(
    private val userService: UserService,
) {
    fun loadUser(userId: Long): UserDetails {
        val user: User = this.userService.getUserById(userId)
        return PrincipalDetails(user)
    }
}
