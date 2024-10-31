package com.relogging.server.security.oauth.provider

import com.relogging.server.domain.user.entity.SocialType

class GoogleUserInfo(
    private val attributes: Map<String, Any>,
) : OAuthUserInfo {
    override fun getProviderId(): String {
        return this.attributes["sub"] as String
    }

    override fun getProvider(): SocialType {
        return SocialType.GOOGLE
    }

    override fun getEmail(): String {
        return this.attributes["email"] as String
    }

    override fun getName(): String {
        return this.attributes["name"] as String
    }

    override fun toString(): String {
        return "GoogleUserInfo(providerId=${getProviderId()}, provider=${getProvider()}, email=${getEmail()}, name=${getName()})"
    }
}
