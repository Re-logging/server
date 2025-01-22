package com.relogging.server.infrastructure.sse.service

import com.relogging.server.infrastructure.sse.repository.SseRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Service
class SseServiceImpl(
    private val sseRepository: SseRepository,
) : SseService {
    private val logger = LoggerFactory.getLogger(javaClass)

    companion object {
        private const val DEFAULT_TIMEOUT = 1800 * 1000L
    }

    override fun connect(userId: Long): SseEmitter {
        val emitterId: String = userId.toString() + "_" + System.currentTimeMillis()

        val emitter: SseEmitter =
            this.sseRepository.save(userId, emitterId, SseEmitter(DEFAULT_TIMEOUT))

        emitter.onCompletion {
            logger.info("onCompletion callback")
            this.sseRepository.delete(emitterId)
        }

        emitter.onTimeout {
            logger.info("onTimeout callback")
            this.sseRepository.delete(emitterId)
        }

        send(emitterId, SseEventName.CONNECT, "SSE 연결 성공: [$emitterId]")

        return emitter
    }

    override fun send(
        emitterId: String,
        name: SseEventName,
        data: Any?,
    ) {
        val emitter = this.sseRepository.get(emitterId)
        try {
            emitter.send(
                SseEmitter.event()
                    .name(name.value)
                    .id(emitterId)
                    .apply {
                        if (data != null) data(data)
                    },
            )
        } catch (e: IOException) {
            this.sseRepository.delete(emitterId)
            throw RuntimeException("데이터 전송 오류!")
        }
    }

    @Scheduled(fixedDelay = 45 * 1000)
    override fun sendHeartbeat() {
        this.sseRepository.emitterMap.forEach { (_, emitter) ->
            try {
                emitter.send(SseEmitter.event().comment("heartbeat"))
            } catch (e: IOException) {
                throw RuntimeException("데이터 전송 오류!")
            }
        }
    }
}

enum class SseEventName(val value: String) {
    CONNECT("connect"),
    COMMENT("comment"),
    REPLY("reply"),
}
