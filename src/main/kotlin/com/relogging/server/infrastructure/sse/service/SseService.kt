package com.relogging.server.infrastructure.sse.service

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface SseService {
    fun connect(userId: Long): SseEmitter

    fun send(
        eventId: String,
        name: SseEventName,
        data: Any?,
    )
}
