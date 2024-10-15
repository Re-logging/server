package com.relogging.server.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    lateinit var createAt: LocalDateTime

    @LastModifiedDate
    @Column(nullable = false)
    lateinit var updateAt: LocalDateTime
}
