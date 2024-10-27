package com.relogging.server.domain.crew.entity

import com.relogging.server.global.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity
class Crew(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_id")
    val id: Long? = null,
    val name: String,
    val description: String,
    val leaderName: String,
    val activityRegion: String,
    val maxMembers: Int,
    val specialNotes: String? = null,
    @field:Enumerated(EnumType.STRING)
    val meetingFrequency: MeetingFrequency,
    val meetingCount: Int,
    val contact: String,
    val isRecuriting: Boolean = true,
    @field:Enumerated(EnumType.STRING)
    val recruitmentType: RecruitmentType,
    val additionalInfo: String? = null,
    val mainImage: String? = null,
    @ElementCollection
    @CollectionTable(name = "crew_image_list", joinColumns = [JoinColumn(name = "crew_id")])
    @Column(name = "image_url")
    val imageList: List<String> = emptyList(),
    @OneToMany(mappedBy = "crew", cascade = [CascadeType.ALL], orphanRemoval = true)
    val crewApplicationList: List<CrewApplication> = emptyList(),
    @OneToMany(mappedBy = "crew", cascade = [CascadeType.ALL], orphanRemoval = true)
    val crewMemberList: List<CrewMember> = emptyList(),
) : BaseEntity()

enum class RecruitmentType {
    INSTANT_APPROVAL,
    LEADER_APPROVAL,
}

enum class MeetingFrequency {
    WEEKLY,
    MONTHLY,
    IRREGULAR,
}
