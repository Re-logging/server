package com.relogging.server.global.oauth.service

import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.domain.user.entity.User
import com.relogging.server.domain.user.service.UserService
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.global.oauth.details.OAuthDetails
import com.relogging.server.global.oauth.provider.GoogleUserInfo
import com.relogging.server.global.oauth.provider.KakaoUserInfo
import com.relogging.server.global.oauth.provider.OAuthUserInfo
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class PrincipalOAuthUserService(
    private val userService: UserService,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(userRequest)
        println("Attributes: ${oAuth2User.attributes}")

        val socialType: SocialType = this.getSocialType(userRequest)
        println("SocialType: $socialType")

        val oAuthUserInfo: OAuthUserInfo =
            this.getOAuth2UserInfo(socialType, oAuth2User.attributes)
        println("OAuth2UserInfo: $oAuthUserInfo")

        var user: User? = this.userService.findUserByEmail(oAuthUserInfo.getEmail())
        if (user == null) {
            // 유저가 없으면 유저생성 고고혓
            user =
                this.userService.createUserWithEssentialInfo(
                    oAuthUserInfo.getName(),
                    oAuthUserInfo.getEmail(),
                    oAuthUserInfo.getName(),
                    socialType,
                    oAuthUserInfo.getProviderId(),
                )
        } else if (user.socialType != socialType) {
            // 해당 이메일로 가입된 소셜 계정이 존재!!
            throw GlobalException(GlobalErrorCode.OAUTH_DUPLICATED_EMAIL)
        }

        return OAuthDetails(user, oAuth2User.attributes)
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
    ): OAuthUserInfo {
        return when (socialType) {
            SocialType.GOOGLE -> GoogleUserInfo(attributes)
            SocialType.KAKAO -> KakaoUserInfo(attributes)
        }
    }
}
