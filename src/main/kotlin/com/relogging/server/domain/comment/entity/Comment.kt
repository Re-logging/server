package com.relogging.server.domain.comment.entity

import com.relogging.server.domain.plogging.entity.PloggingEvent
import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup
import com.relogging.server.domain.user.entity.User
import com.relogging.server.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "comment")
class Comment(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "comment_id")
    val id: Long? = null,
    var content: String,
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    var user: User,
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "plogging_event_id")
    var ploggingEvent: PloggingEvent? = null,
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "plogging_meetup_id")
    var ploggingMeetup: PloggingMeetup? = null,
    @field:ManyToOne
    @field:JoinColumn(name = "parent_comment_id")
    var parentComment: Comment? = null,
    @field:OneToMany(mappedBy = "parentComment")
    var childComment: MutableList<Comment> = mutableListOf(),
) : BaseEntity()