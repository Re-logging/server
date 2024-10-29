package com.relogging.server.global.security.oauth.provider

import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException

class KakaoUserInfo(
    private val attributes: Map<String, Any>,
) : OAuthUserInfo {
    override fun getProviderId(): String {
        return this.attributes["id"].toString()
    }

    override fun getProvider(): SocialType {
        return SocialType.KAKAO
    }

    override fun getEmail(): String {
        val kakaoAccount =
            attributes["kakao_account"] as? Map<*, *>
                ?: throw GlobalException(GlobalErrorCode.OAUTH_SERVICE_ERROR)

        return kakaoAccount["email"] as String
    }

    override fun getName(): String {
        val properties =
            attributes["properties"] as? Map<*, *>
                ?: throw GlobalException(GlobalErrorCode.OAUTH_SERVICE_ERROR)

        return properties["nickname"] as String
    }

    override fun toString(): String {
        return "KakaoUserInfo(providerId=${getProviderId()}, provider=${getProvider()}, email=${getEmail()}, name=${getName()})"
    }
}
