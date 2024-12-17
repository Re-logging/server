package com.relogging.server.infrastructure.admin.entity

import com.relogging.server.global.BaseEntity
import com.relogging.server.infrastructure.kakao.dto.KakaoTokenResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Admin(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "admin_id")
    val id: Long? = null,
    @field:Column(unique = true)
    val kakaoId: Long,
    var accessToken: String,
    var expiresIn: Int,
    var refreshToken: String,
    var refreshTokenExpiresIn: Int,
) : BaseEntity() {
    fun updateTokens(tokens: KakaoTokenResponse) {
        this.accessToken = tokens.accessToken
        this.expiresIn = tokens.expiresIn
        this.refreshToken = tokens.refreshToken
        this.refreshTokenExpiresIn = tokens.refreshTokenExpiresIn
    }
}
