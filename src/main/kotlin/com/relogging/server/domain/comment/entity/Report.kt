package com.relogging.server.domain.comment.entity

import com.relogging.server.domain.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "report")
class Report(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "report_id")
    var id: Long? = null,
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    var user: User,
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "comment_id")
    var comment: Comment,
    @field:Column(columnDefinition = "TEXT")
    val reason: String,
)
