package com.relogging.server.domain.ploggingMeetup.entity

import com.relogging.server.domain.comment.entity.Comment
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupUpdateRequest
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
import jakarta.persistence.ManyToOne
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
    var title: String,
    var content: String,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var location: String,
    var activityHours: String,
    var region: String,
    var imageUrl: String?,
    var contactPerson: String,
    var contactNumber: String,
    var participationTarget: String,
    var supportDetails: String,
    var registrationLink: String,
    var hits: Long = 0,
    @field:ManyToOne(fetch = FetchType.LAZY)
    val host: User,
    @field:OneToMany(mappedBy = "ploggingMeetup", cascade = [CascadeType.ALL])
    val commentList: MutableList<Comment> = mutableListOf(),
) : BaseEntity() {
    fun checkUserAccess(user: User) {
        if (this.host.id != user.id) {
            throw GlobalException(GlobalErrorCode.PLOGGING_MEETUP_NOT_AUTHORIZED)
        }
    }

    fun update(request: PloggingMeetupUpdateRequest) {
        request.title?.let { this.title = it }
        request.content?.let { this.content = it }
        request.location?.let { this.location = it }
        request.region?.let { this.region = it }
        request.startDate?.let { this.startDate = it }
        request.endDate?.let { this.endDate = it }
        request.participantTarget?.let { this.participationTarget = it }
        request.supportDetails?.let { this.supportDetails = it }
        request.activityHours?.let { this.activityHours = it }
        request.contactPerson?.let { this.contactPerson = it }
        request.contactNumber?.let { this.contactNumber = it }
        request.registrationLink?.let { this.registrationLink = it }
    }
}
