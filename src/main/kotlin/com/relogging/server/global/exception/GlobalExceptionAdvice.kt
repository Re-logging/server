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
                status = e.globalErrorCode.status.value(),
                error = e.globalErrorCode.errorCode,
                message = e.globalErrorCode.message,
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
                status = HttpStatus.BAD_REQUEST.value(),
                error = "VALIDATION_FAILED",
                message = errorMessage,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(Throwable::class)
    fun handleAllExceptions(ex: Throwable): ResponseEntity<ErrorResponse> {
        val status =
            when (ex) {
                is IllegalArgumentException -> HttpStatus.BAD_REQUEST
                is NullPointerException -> HttpStatus.INTERNAL_SERVER_ERROR
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }
        val errorResponse =
            ErrorResponse(
                status = status.value(),
                error = status.reasonPhrase,
                message = ex.message ?: "An unexpected error occurred",
            )
        return ResponseEntity(errorResponse, status)
    }
}
