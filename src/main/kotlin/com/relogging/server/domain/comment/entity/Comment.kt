package com.relogging.server.domain.comment.entity

import com.relogging.server.domain.notification.entity.Notification
import com.relogging.server.domain.plogging.entity.PloggingEvent
import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup
import com.relogging.server.domain.user.entity.User
import com.relogging.server.global.BaseEntity
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
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
    var isDeleted: Boolean = false,
    @field:OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var reportList: MutableList<Report> = mutableListOf(),
    @field:OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var notification: Notification? = null,
) : BaseEntity() {
    fun delete() {
        this.isDeleted = true
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun addReply(reply: Comment) {
        childComment.add(reply)
        reply.parentComment = this
    }

    fun validateReplyDepth() {
        if (parentComment != null) {
            throw GlobalException(GlobalErrorCode.COMMENT_DEPTH_EXCEEDED)
        }
    }

    fun requirePloggingMeetup(): PloggingMeetup {
        return this.ploggingMeetup
            ?: throw GlobalException(GlobalErrorCode.INTERNAL_SERVER_ERROR)
    }

    fun requireParentComment(): Comment {
        return this.parentComment ?: throw GlobalException(GlobalErrorCode.INTERNAL_SERVER_ERROR)
    }
}
