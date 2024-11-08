package com.relogging.server.security.service

import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.domain.user.entity.User
import com.relogging.server.domain.user.service.UserService
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.redis.service.RefreshTokenService
import com.relogging.server.security.jwt.provider.TokenProvider
import com.relogging.server.security.oauth.provider.GoogleUserInfo
import com.relogging.server.security.oauth.provider.KakaoUserInfo
import com.relogging.server.security.oauth.provider.OAuthUserInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class AuthServiceImpl(
    private val tokenProvider: TokenProvider,
    private val refreshTokenService: RefreshTokenService,
    private val userService: UserService,
    private val webClient: WebClient,

    @Value("\${oauth.google.client-id}")
    private val googleClientId: String,
    @Value("\${oauth.google.client-secret}")
    private val googleClientSecret: String,
//    @Value("\${oauth.google.redirect-uri}")
//    private val googleRedirectUri: String,
    @Value("\${oauth.google.authorization-grant-type}")
    private val googleGrantType: String,
    @Value("\${oauth.google.token-uri}")
    private val googleTokenUri: String,
    @Value("\${oauth.google.user-info-uri}")
    private val googleUserInfoUri: String,

    @Value("\${oauth.kakao.client-id}")
    private val kakaoClientId: String,
    @Value("\${oauth.kakao.client-secret}")
    private val kakaoClientSecret: String,
//    @Value("\${oauth.kakao.redirect-uri}")
//    private val kakaoRedirectUri: String,
    @Value("\${oauth.kakao.authorization-grant-type}")
    private val kakaoGrantType: String,
    @Value("\${oauth.kakao.token-uri}")
    private val kakaoTokenUri: String,
    @Value("\${oauth.kakao.user-info-uri}")
    private val kakaoUserInfoUri: String,
) : AuthService {
    override fun reissue(refreshToken: String): String {
        val userId: Long = this.tokenProvider.getUserId(refreshToken)
        if (this.refreshTokenService.validRefreshToken(userId, refreshToken)) {
            return this.tokenProvider.createAccessToken(userId)
        } else {
            throw GlobalException(GlobalErrorCode.UNAUTHORIZED)
        }
    }

    override fun oAuthLogin(socialType: SocialType, code: String, redirectUri: String): User {
        val accessToken = when (socialType) {
            SocialType.GOOGLE -> this.getSocialAccessToken(
                googleTokenUri,
                code,
                googleClientId,
                googleClientSecret,
                redirectUri,
                googleGrantType
            )

            SocialType.KAKAO -> this.getSocialAccessToken(
                kakaoTokenUri,
                code,
                kakaoClientId,
                kakaoClientSecret,
                redirectUri,
                kakaoGrantType
            )
        }
        val oAuthUserInfo = this.getSocialUserInfo(socialType, accessToken)

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

        return user
    }

    override fun getSocialAccessToken(
        tokenUri: String,
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        grantType: String
    ): String {
        val response = webClient.post()
            .uri(tokenUri)
            .bodyValue(
                mapOf(
                    "code" to code,
                    "client_id" to clientId,
                    "client_secret" to clientSecret,
                    "redirect_uri" to redirectUri,
                    "grant_type" to grantType
                )
            )
            .retrieve()
            .bodyToMono<Map<String, Any>>()
            .block()

        return response?.get("access_token") as String

    }

    override fun getSocialUserInfo(socialType: SocialType, accessToken: String): OAuthUserInfo =
        when (socialType) {
            SocialType.GOOGLE -> this.getGoogleUserInfo(accessToken)
            SocialType.KAKAO -> this.getKakaoUserInfo(accessToken)
        }

    override fun getGoogleUserInfo(accessToken: String): GoogleUserInfo {
        val response = webClient.get()
            .uri("$googleUserInfoUri?access_token=$accessToken")
            .retrieve()
            .bodyToMono<Map<String, Any>>()
            .block()

        return GoogleUserInfo(response!!)
    }

    override fun getKakaoUserInfo(accessToken: String): KakaoUserInfo {
        val response = webClient.get()
            .uri(kakaoUserInfoUri)
            .header("Authorization", "Bearer $accessToken")
            .header("Content-type", "${MediaType.APPLICATION_JSON};charset=utf-8")
            .retrieve()
            .bodyToMono<Map<String, Any>>()
            .block()

        return KakaoUserInfo(response!!)
    }
}
