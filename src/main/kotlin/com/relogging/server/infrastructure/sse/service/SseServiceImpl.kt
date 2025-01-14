package com.relogging.server.infrastructure.sse.service

import com.relogging.server.infrastructure.sse.repository.SseRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Service
class SseServiceImpl(
    private val sseRepository: SseRepository,
) : SseService {
    private val logger = LoggerFactory.getLogger(javaClass)

    companion object {
        private const val DEFAULT_TIMEOUT = 1 * 1000L
    }

    override fun connect(userId: Long): SseEmitter {
        val eventId: String = userId.toString() + "_" + System.currentTimeMillis()

        val emitter: SseEmitter = this.sseRepository.save(eventId, SseEmitter(DEFAULT_TIMEOUT))

        emitter.onCompletion {
            logger.info("onCompletion callback")
            this.sseRepository.delete(eventId)
        }

        emitter.onTimeout {
            logger.info("onTimeout callback")
            this.sseRepository.delete(eventId)
        }

        send(eventId, SseEventName.CONNECT, emitter, "SSE 연결 성공: [$eventId]")

        return emitter
    }

    override fun send(
        eventId: String,
        name: SseEventName,
        emitter: SseEmitter,
        data: Any,
    ) {
        try {
            emitter.send(
                SseEmitter.event()
                    .name(name.value)
                    .id(eventId)
                    .data(data),
            )
        } catch (e: IOException) {
            this.sseRepository.delete(eventId)
            throw RuntimeException("데이터 전송 오류!")
        }
    }
}

enum class SseEventName(val value: String) {
    CONNECT("connect"),
    COMMENT("comment"),
    REPLY("reply"),
}
