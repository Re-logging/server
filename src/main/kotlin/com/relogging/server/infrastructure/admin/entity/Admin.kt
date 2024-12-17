package com.relogging.server.infrastructure.admin.entity

import com.relogging.server.global.BaseEntity
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
    var tokenExpiresIn: Long,
    var refreshToken: String,
    var refreshTokenExpiresIn: Long,
) : BaseEntity() {
    fun updateTokens(
        accessToken: String,
        tokenExpiresIn: Long,
        refreshToken: String,
        refreshTokenExpiresIn: Long,
    ) {
        this.accessToken = accessToken
        this.tokenExpiresIn = tokenExpiresIn
        this.refreshToken = refreshToken
        this.refreshTokenExpiresIn = refreshTokenExpiresIn
    }
}
