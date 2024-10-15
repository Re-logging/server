package com.relogging.server.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(annotations = [RestController::class])
class GlobalExceptionAdvice {
    @ExceptionHandler(GlobalException::class)
    fun handleCommonException(e: GlobalException): ResponseEntity<ErrorResponse> {
        return ResponseEntity<ErrorResponse>(
            ErrorResponse(
                e.globalErrorCode.status,
                e.globalErrorCode.errorCode,
                e.globalErrorCode.message,
            ),
            e.globalErrorCode.status,
        )
    }
}
