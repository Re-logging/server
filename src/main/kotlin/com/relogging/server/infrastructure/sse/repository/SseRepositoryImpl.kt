package com.relogging.server.infrastructure.sse.repository

import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Repository
class SseRepositoryImpl(
    private val emitterMap: ConcurrentHashMap<String, SseEmitter> = ConcurrentHashMap(),
) : SseRepository {
    override fun save(
        connectionId: String,
        emitter: SseEmitter,
    ): SseEmitter {
        this.emitterMap[connectionId] = emitter
        return emitter
    }

    override fun delete(connectionId: String) {
        this.emitterMap.remove(connectionId)
    }
}
