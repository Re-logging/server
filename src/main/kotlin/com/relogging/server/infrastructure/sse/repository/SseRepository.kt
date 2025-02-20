package com.relogging.server.infrastructure.sse.repository

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

interface SseRepository {
    val emitterMap: ConcurrentHashMap<String, SseEmitter>
    val userMap: ConcurrentHashMap<Long, MutableList<String>>

    fun save(
        userId: Long,
        emitterId: String,
        emitter: SseEmitter,
    ): SseEmitter

    fun delete(emitterId: String)

    fun get(emitterId: String): SseEmitter

    fun getEmitterIdList(userId: Long): List<String>
}
