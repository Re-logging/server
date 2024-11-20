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
            ErrorResponse(e.globalErrorCode),
            e.globalErrorCode.status,
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val fieldErrors =
            e.bindingResult.allErrors
                .filterIsInstance<FieldError>()
                .map { error -> "${error.field}: ${error.defaultMessage}" }
        val errorMessage = fieldErrors.joinToString(", ")

        return ResponseEntity.badRequest().body(
            ErrorResponse(GlobalErrorCode.BAD_REQUEST, errorMessage),
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse(GlobalErrorCode.BAD_REQUEST, e.message),
        )
    }

    // MissingServletRequestParameterException: 필수 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParameterException(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse(GlobalErrorCode.BAD_REQUEST, e.message),
        )
    }

    // JsonParseException: JSON 구조 오류
    @ExceptionHandler(JsonParseException::class)
    fun handleJsonParseException(e: JsonParseException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse(GlobalErrorCode.BAD_REQUEST, e.message),
        )
    }

    // 모든 예외 처리: 기본 500 서버 오류
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR, e.message),
        )
    }
}
