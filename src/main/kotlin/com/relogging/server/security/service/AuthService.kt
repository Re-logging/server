package com.relogging.server.security.service

import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.domain.user.entity.User
import com.relogging.server.security.oauth.provider.GoogleUserInfo
import com.relogging.server.security.oauth.provider.KakaoUserInfo
import com.relogging.server.security.oauth.provider.OAuthUserInfo

interface AuthService {
    fun reissue(refreshToken: String): String

    fun oAuthLogin(
        socialType: SocialType,
        code: String,
        redirectUri: String,
    ): User

    fun getSocialAccessToken(
        socialType: SocialType,
        code: String,
        redirectUri: String,
    ): String

    fun getGoogleAccessToken(
        code: String,
        redirectUri: String,
    ): String

    fun getKakaoAccessToken(
        code: String,
        redirectUri: String,
    ): String

    fun getSocialUserInfo(
        socialType: SocialType,
        accessToken: String,
    ): OAuthUserInfo

    fun getGoogleUserInfo(accessToken: String): GoogleUserInfo

    fun getKakaoUserInfo(accessToken: String): KakaoUserInfo

    fun tempLogin(
        name: String,
        email: String,
    ): User
}
