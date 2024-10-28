package com.relogging.server.domain.crew.entity

import com.relogging.server.domain.user.entity.User
import com.relogging.server.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class CrewMember(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_member_id")
    val id: Long? = null,
    @Enumerated(EnumType.STRING)
    val role: CrewRole,
    val joinedAt: LocalDateTime = LocalDateTime.now(),
    @ManyToOne(fetch = FetchType.LAZY)
    val crew: Crew,
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,
) : BaseEntity() {
    companion object {
        fun createCrewLeader(
            user: User,
            crew: Crew,
        ): CrewMember {
            return CrewMember(
                role = CrewRole.LEADER,
                user = user,
                crew = crew,
            )
        }
    }
}

enum class CrewRole {
    LEADER,
    MEMBER,
}
