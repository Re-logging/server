package com.relogging.server.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(annotations = [RestController::class])
class GlobalExceptionAdvice {
    @ExceptionHandler(GlobalException::class)
    fun handleCommonException(e: GlobalException): ResponseEntity<ErrorResponse> =
        ResponseEntity<ErrorResponse>(
            ErrorResponse(
                e.globalErrorCode.status,
                e.globalErrorCode.errorCode,
                e.globalErrorCode.message,
            ),
            e.globalErrorCode.status,
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val fieldErrors =
            e.bindingResult.allErrors
                .filterIsInstance<FieldError>()
                .map { error -> "${error.field}: ${error.defaultMessage}" }

        val errorMessage = fieldErrors.joinToString(", ")

        return ResponseEntity(
            ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_FAILED",
                errorMessage,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }
}
