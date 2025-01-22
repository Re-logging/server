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
        emitterId: String,
        emitter: SseEmitter,
    ): SseEmitter {
        this.emitterMap[emitterId] = emitter
        if (this.userMap.contains(userId)) {
            this.userMap[userId]!!.add(emitterId)
        } else {
            this.userMap[userId] = mutableListOf(emitterId)
        }
        return emitter
    }

    override fun delete(emitterId: String) {
        val userId: Long = emitterId.substringBefore('_').toLong()
        this.emitterMap.remove(emitterId)
        if (this.userMap[userId]?.size == 0) {
            this.userMap.remove(userId)
        }
    }

    override fun get(emitterId: String): SseEmitter = this.emitterMap[emitterId] ?: throw GlobalException(GlobalErrorCode.EMITTER_NOT_FOUND)

    override fun getEmitterIdList(userId: Long): List<String> = this.userMap[userId]?.toList() ?: emptyList()
}
