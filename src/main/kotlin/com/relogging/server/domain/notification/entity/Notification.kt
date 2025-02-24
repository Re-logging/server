package com.relogging.server.domain.notification.entity

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.user.entity.User
import com.relogging.server.global.BaseEntity
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "notification")
class Notification(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "comment_id")
    val id: Long? = null,
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    val user: User,
    val type: NotificationType,
    @field:OneToOne(mappedBy = "notification")
    val comment: Comment? = null,
) : BaseEntity() {
    fun requireComment(): Comment {
        return this.comment ?: throw GlobalException(GlobalErrorCode.INTERNAL_SERVER_ERROR)
    }
}

enum class NotificationType {
    COMMENT,
    REPLY,
}
