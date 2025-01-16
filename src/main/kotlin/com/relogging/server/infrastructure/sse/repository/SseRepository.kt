package com.relogging.server.infrastructure.sse.repository

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

interface SseRepository {
    val emitterMap: ConcurrentHashMap<String, SseEmitter>
    val userMap: ConcurrentHashMap<Long, MutableList<String>>

    fun save(
        userId: Long,
        eventId: String,
        emitter: SseEmitter,
    ): SseEmitter

    fun delete(eventId: String)

    fun get(eventId: String): SseEmitter

    fun getEventIdList(userId: Long): List<String>
}
