package com.relogging.server.domain.ploggingMeetup.entity

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.global.BaseEntity
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
@Table(name = "plogging_meetup")
class PloggingMeetup(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "plogging_meetup_id")
    val id: Long? = null,
    val title: String,
    val content: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val location: String,
    val activityHours: String,
    val region: String,
    var imageUrl: String,
    val contactPerson: String,
    val contactNumber: String,
    val participationTarget: String,
    val supportDetails: String,
    val registrationLink: String,
    var hits: Long = 0,
    @field:OneToMany(mappedBy = "ploggingMeetup", cascade = [CascadeType.ALL])
    val commentList: MutableList<Comment> = mutableListOf(),
) : BaseEntity()
