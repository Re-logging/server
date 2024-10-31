package com.relogging.server.security.oauth.provider

import com.relogging.server.domain.user.entity.SocialType

interface OAuthUserInfo {
    fun getProviderId(): String

    fun getProvider(): SocialType

    fun getEmail(): String

    fun getName(): String
}
