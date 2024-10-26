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

@Entity
class CrewApplication(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "crew_application_id")
    val id: Long? = null,
    @field:Enumerated(EnumType.STRING)
    val status: ApplicationStatus,
    @field:ManyToOne(fetch = FetchType.LAZY)
    val user: User,
    @field:ManyToOne(fetch = FetchType.LAZY)
    val crew: Crew,
) : BaseEntity()

enum class ApplicationStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
}
