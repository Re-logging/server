package com.relogging.server.infrastructure.sse.repository

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface SseRepository {
    fun save(
        eventId: String,
        emitter: SseEmitter,
    ): SseEmitter

    fun delete(eventId: String)
}
