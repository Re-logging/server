package com.relogging.server.oauth

import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.oauth.provider.GoogleUserInfo
import com.relogging.server.oauth.provider.KakaoUserInfo
import com.relogging.server.oauth.provider.OAuth2UserInfo
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserService : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(userRequest)
        println("Attributes: ${oAuth2User.attributes}")

        val socialType: SocialType = this.getSocialType(userRequest)
        println("SocialType: $socialType")

        val oAuth2UserInfo: OAuth2UserInfo =
            this.getOAuth2UserInfo(socialType, oAuth2User.attributes)
        println("OAuth2UserInfo: $oAuth2UserInfo")

        return oAuth2User
    }

    fun getSocialType(userRequest: OAuth2UserRequest): SocialType {
        val registrationId: String = userRequest.clientRegistration.registrationId

        return when (registrationId) {
            "google" -> SocialType.GOOGLE
            "kakao" -> SocialType.KAKAO
            else -> throw GlobalException(GlobalErrorCode.OAUTH_UNEXPECTED_ERROR)
        }
    }

    fun getOAuth2UserInfo(
        socialType: SocialType,
        attributes: Map<String, Any>,
    ): OAuth2UserInfo {
        return when (socialType) {
            SocialType.GOOGLE -> GoogleUserInfo(attributes)
            SocialType.KAKAO -> KakaoUserInfo(attributes)
        }
    }
}
