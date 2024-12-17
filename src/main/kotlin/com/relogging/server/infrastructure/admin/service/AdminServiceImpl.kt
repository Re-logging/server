package com.relogging.server.infrastructure.admin.service

import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.infrastructure.admin.dto.AdminRequest
import com.relogging.server.infrastructure.admin.entity.Admin
import com.relogging.server.infrastructure.admin.repository.AdminRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    @Value("\${kakao.admin.client-id}")
    private val clientId: String,
    @Value("\${kakao.admin.redirect-uri}")
    private val redirectUri: String,
    @Value("\${kakao.admin.client-secret}")
    private val clientSecret: String,
    private val webClient: WebClient,
) : AdminService {
    @Transactional
    override fun adminKakaoLogin(request: AdminRequest): String {
        // 1. 카카오 서버로부터 토큰 발급받기
        val tokens = getKakaoTokens(request.code)

        // 2. 카카오 액세스 토큰으로 사용자 정보 가져오기
        val kakaoId = getKakaoAdminInfo(tokens.accessToken)

        // 3. DB에 토큰 정보 저장/업데이트
        return saveOrUpdateAdminTokens(kakaoId, tokens)
    }

    private fun getKakaoTokens(code: String): KakaoTokens {
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
                .bodyToMono<Map<String, Any>>()
                .block() ?: throw RuntimeException("Failed to get Kakao tokens")

        return KakaoTokens(
            accessToken = response.get("access_token") as String,
            expiresIn = (response.get("expires_in") as Number).toLong(),
            refreshToken = response.get("refresh_token") as String,
            refreshTokenExpiresIn = (response.get("refresh_token_expires_in") as Number).toLong(),
        )
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
        tokens: KakaoTokens,
    ): String {
        val admin = adminRepository.findByKakaoId(kakaoId)
        if (admin != null) {
            admin.updateTokens(
                accessToken = tokens.accessToken,
                refreshToken = tokens.refreshToken,
                tokenExpiresIn = tokens.expiresIn,
                refreshTokenExpiresIn = tokens.refreshTokenExpiresIn,
            )
            return "update"
        } else {
            val newAdmin =
                Admin(
                    kakaoId = kakaoId,
                    accessToken = tokens.accessToken,
                    tokenExpiresIn = tokens.expiresIn,
                    refreshToken = tokens.refreshToken,
                    refreshTokenExpiresIn = tokens.refreshTokenExpiresIn,
                )
            adminRepository.save(newAdmin)
            return "sign-in"
        }
    }

    override fun findById(id: Long): Admin {
        return adminRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.ADMIN_NOT_FOUND)
        }
    }

    override fun findAll(): List<Admin> {
        return adminRepository.findAll()
    }
}

private data class KakaoTokens(
    val accessToken: String,
    val expiresIn: Long,
    val refreshToken: String,
    val refreshTokenExpiresIn: Long,
)
