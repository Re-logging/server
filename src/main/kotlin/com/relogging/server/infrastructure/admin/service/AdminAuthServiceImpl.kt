package com.relogging.server.infrastructure.admin.service

import com.relogging.server.infrastructure.admin.dto.AdminConverter
import com.relogging.server.infrastructure.admin.dto.AdminRequest
import com.relogging.server.infrastructure.admin.entity.Admin
import com.relogging.server.infrastructure.admin.repository.AdminRepository
import com.relogging.server.infrastructure.kakao.dto.KakaoTokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class AdminAuthServiceImpl(
    private val adminRepository: AdminRepository,
    @Value("\${kakao.admin.client-id}")
    private val clientId: String,
    @Value("\${kakao.admin.redirect-uri}")
    private val redirectUri: String,
    @Value("\${kakao.admin.client-secret}")
    private val clientSecret: String,
    private val webClient: WebClient,
) : AdminAuthService {
    @Transactional
    override fun kakaoLogin(request: AdminRequest): String {
        val tokens = getKakaoTokens(request.code)
        val kakaoId = getKakaoAdminInfo(tokens.accessToken)
        return saveOrUpdateAdminTokens(kakaoId, tokens)
    }

    private fun getKakaoTokens(code: String): KakaoTokenResponse {
        val url = "https://kauth.kakao.com/oauth/token"

        val formData =
            LinkedMultiValueMap<String, String>().apply {
                add("grant_type", "authorization_code")
                add("client_id", clientId)
                add("redirect_uri", redirectUri)
                add("code", code)
                add("client_secret", clientSecret)
            }

        val response =
            webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono<KakaoTokenResponse>()
                .block() ?: throw RuntimeException("Failed to get Kakao tokens")

        return response
    }

    private fun getKakaoAdminInfo(accessToken: String): Long {
        val url = "https://kapi.kakao.com/v2/user/me"

        val response =
            webClient
                .get()
                .uri(url)
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .bodyToMono<Map<String, Any>>()
                .block() ?: throw RuntimeException("Failed to get Kakao user info")

        return (response.get("id") as Number).toLong()
    }

    private fun saveOrUpdateAdminTokens(
        kakaoId: Long,
        tokens: KakaoTokenResponse,
    ): String {
        val admin = adminRepository.findByKakaoId(kakaoId)
        if (admin != null) {
            admin.updateTokens(tokens)
            return "update"
        } else {
            val newAdmin = AdminConverter.toEntity(kakaoId, tokens)
            adminRepository.save(newAdmin)
            return "sign-in"
        }
    }

    @Transactional
    override fun kakaoTokenRefresh(admin: Admin) {
        val tokens = refreshKakaoTokens(admin.refreshToken)
        admin.updateTokens(tokens)
    }

    private fun refreshKakaoTokens(refreshToken: String): KakaoTokenResponse {
        val url = "https://kauth.kakao.com/oauth/token"

        val formData =
            LinkedMultiValueMap<String, String>().apply {
                add("grant_type", "refresh_token")
                add("client_id", clientId)
                add("refresh_token", refreshToken)
                add("client_secret", clientSecret)
            }

        val response =
            webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono<KakaoTokenResponse>()
                .block() ?: throw RuntimeException("Failed to refresh Kakao tokens")

        return response
    }
}
