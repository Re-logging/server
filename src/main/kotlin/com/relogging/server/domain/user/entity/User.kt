package com.relogging.server.domain.user.entity

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.crew.entity.CrewApplication
import com.relogging.server.domain.crew.entity.CrewMember
import com.relogging.server.global.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "user")
class User(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "user_id")
    val id: Long? = null,
    val name: String,
    @field:Column(unique = true)
    val email: String,
    val nickname: String,
    val profileImage: String? = null,
    @field:Enumerated(EnumType.STRING)
    val socialType: SocialType,
    val phoneNumber: String? = null,
    @field:OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val crewApplicationList: MutableList<CrewApplication> = mutableListOf(),
    @field:OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val crewMemberList: MutableList<CrewMember> = mutableListOf(),
    val providerId: String,
    val role: Role = Role.USER,
    @field:OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val commentList: MutableList<Comment> = mutableListOf(),
) : BaseEntity()

enum class SocialType {
    GOOGLE,
    KAKAO,
}

enum class Role(val value: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
}
