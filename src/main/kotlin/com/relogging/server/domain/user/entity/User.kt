package com.relogging.server.domain.user.entity

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
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val crewApplicationList: List<CrewApplication> = emptyList(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val crewMemberList: List<CrewMember> = emptyList(),
    val providerId: String,
//    @OneToOne(
//        mappedBy = "user",
//        cascade = [CascadeType.ALL],
//        fetch = FetchType.LAZY,
//        orphanRemoval = true
//    )
//    var socialAccount: SocialAccount,
    val role: Role = Role.USER
) : BaseEntity()

enum class SocialType {
    GOOGLE,
    KAKAO,
}

enum class Role {
    USER,
    ADMIN
}
