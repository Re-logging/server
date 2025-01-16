package com.relogging.server.infrastructure.sse.repository

import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Repository
class SseRepositoryImpl(
    override val emitterMap: ConcurrentHashMap<String, SseEmitter> = ConcurrentHashMap(),
    override val userMap: ConcurrentHashMap<Long, MutableList<String>> = ConcurrentHashMap(),
) : SseRepository {
    override fun save(
        userId: Long,
        eventId: String,
        emitter: SseEmitter,
    ): SseEmitter {
        this.emitterMap[eventId] = emitter
        if (this.userMap.contains(userId)) {
            this.userMap[userId]!!.add(eventId)
        } else {
            this.userMap[userId] = mutableListOf(eventId)
        }
        return emitter
    }

    override fun delete(eventId: String) {
        val userId: Long = eventId.substringBefore('_').toLong()
        this.emitterMap.remove(eventId)
        if (this.userMap[userId]?.size == 0) {
            this.userMap.remove(userId)
        }
    }

    override fun get(eventId: String): SseEmitter = this.emitterMap[eventId] ?: throw GlobalException(GlobalErrorCode.EMITTER_NOT_FOUND)

    override fun getEventIdList(userId: Long): List<String> = this.userMap[userId]?.toList() ?: emptyList()
}
