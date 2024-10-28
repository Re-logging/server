package com.relogging.server.domain.socialAccount.entity

import com.relogging.server.domain.user.entity.SocialType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "social_info")
class SocialAccount(
    @Id
    @Column(name = "user_id")
    val userId: Long,
    @field:Enumerated(EnumType.STRING)
    val socialType: SocialType,
    val providerId: String,
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "user_id")
//    val user: User,
)
