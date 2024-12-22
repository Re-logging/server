package com.relogging.server.global.exception

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class ErrorResponse(
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String,
) {
    constructor(globalErrorCode: GlobalErrorCode) : this(
        status = globalErrorCode.status.value(),
        error = globalErrorCode.errorCode,
        message = globalErrorCode.message,
    )

    constructor(globalErrorCode: GlobalErrorCode, message: String?) : this(
        status = globalErrorCode.status.value(),
        error = globalErrorCode.errorCode,
        message = message ?: globalErrorCode.message,
    )
}
