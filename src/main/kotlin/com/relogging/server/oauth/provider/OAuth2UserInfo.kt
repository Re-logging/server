package com.relogging.server.oauth.provider

import com.relogging.server.domain.user.entity.SocialType

interface OAuth2UserInfo {
    fun getProviderId(): String

    fun getProvider(): SocialType

    fun getEmail(): String

    fun getName(): String
}
