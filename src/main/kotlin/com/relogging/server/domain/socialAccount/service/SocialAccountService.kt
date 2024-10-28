package com.relogging.server.domain.socialAccount.service

import com.relogging.server.domain.socialAccount.entity.SocialAccount
import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.domain.user.entity.User

interface SocialAccountService {
    fun createSocialAccount(
        user: User,
        socialType: SocialType,
        providerId: String,
    ): SocialAccount
}
