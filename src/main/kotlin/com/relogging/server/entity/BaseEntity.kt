package com.relogging.server.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseEntity {
    @CreatedDate @Column(nullable = false, updatable = false) lateinit var createAt: LocalDateTime
    @LastModifiedDate @Column(nullable = false) lateinit var updateAt: LocalDateTime
}
