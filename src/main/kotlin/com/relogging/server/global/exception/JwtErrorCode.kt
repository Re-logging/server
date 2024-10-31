package com.relogging.server.global.exception

import org.springframework.http.HttpStatus

enum class JwtErrorCode(
    val status: HttpStatus,
    val errorCode: String,
    val message: String,
) {
    JWT_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-001", "유효하지 않는 토큰입니다."),

    JWT_MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, "JWT-002", "잘못된 형식의 토큰입니다."),

    JWT_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-003", "유효기간이 만료된 토큰입니다."),

    JWT_UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-004", "지원하지 않는 형식의 토큰입니다."),
}
