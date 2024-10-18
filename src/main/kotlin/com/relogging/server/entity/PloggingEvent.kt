package com.relogging.server.entity

import jakarta.persistence.*
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
    val manager: String,
    val phoneNumber: String,
) : BaseEntity()
