package com.relogging.server.domain.user.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.crew.entity.CrewApplication
import com.relogging.server.domain.crew.entity.CrewMember
import com.relogging.server.domain.image.entity.Image
import com.relogging.server.domain.notification.entity.Notification
import com.relogging.server.global.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user")
class User(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "user_id")
    val id: Long? = null,
    var name: String,
    @field:Column(unique = true)
    val email: String,
    var nickname: String,
    @field:OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var profileImage: Image? = null,
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
    @field:OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val notificationList: MutableList<Notification> = mutableListOf(),
) : BaseEntity()

enum class SocialType(val value: String) {
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    ;

    @JsonValue
    fun toValue(): String = value

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): SocialType {
            return entries.find { it.value.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown enum value: $value")
        }
    }
}

enum class Role(val value: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
}
