package com.relogging.server.global.exception

import org.springframework.boot.json.JsonParseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
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

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Invalid JSON format",
                message = e.localizedMessage ?: "Invalid request body",
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    // MissingServletRequestParameterException: 필수 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParameterException(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Missing required parameter",
                message = e.message ?: "Required parameter is missing",
            ),
        )
    }

    // JsonParseException: JSON 구조 오류
    @ExceptionHandler(JsonParseException::class)
    fun handleJsonParseException(ex: JsonParseException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Invalid JSON",
                message = ex.localizedMessage ?: "JSON structure is incorrect",
            ),
        )
    }

    // 모든 예외 처리: 기본 500 서버 오류
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorResponse(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error = "Internal server error",
                message = ex.localizedMessage ?: "An error occurred",
            ),
        )
    }
}
