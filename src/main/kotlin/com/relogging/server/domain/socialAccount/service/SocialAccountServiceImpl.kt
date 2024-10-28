package com.relogging.server.domain.socialAccount.service

import com.relogging.server.domain.socialAccount.entity.SocialAccount
import com.relogging.server.domain.socialAccount.repository.SocialAccountRepository
import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class SocialAccountServiceImpl(
    private val socialAccountRepository: SocialAccountRepository,
) : SocialAccountService {
    override fun createSocialAccount(
        user: User,
        socialType: SocialType,
        providerId: String,
    ): SocialAccount {
        return this.socialAccountRepository.save(
            SocialAccount(
                user.id!!,
                socialType,
                providerId,
                user,
            ),
        )
    }
}
