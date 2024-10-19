package com.relogging.server.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "plogging_event")
class PloggingEvent(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "plogging_event_id")
    val id: Long? = null,
    val title: String,
    val content: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val location: String,
    val participantLimit: Int,
    val currentParticipants: Int,
    val isVolunteerWork: Boolean,
    val organizerName: String,
    val region: String,
    @field:Column(columnDefinition = "BIGINT DEFAULT 0")
    var hits: Long = 0,
    @field:OneToMany(mappedBy = "ploggingEvent", cascade = [CascadeType.ALL])
    var imageList: List<Image> = ArrayList(),
    val managerName: String,
    val phoneNumber: String,
    val participationTarget: String,
    val volunteerScore: String,
) : BaseEntity()
