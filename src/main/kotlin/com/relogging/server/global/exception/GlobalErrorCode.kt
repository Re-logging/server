package com.relogging.server.global.exception

import org.springframework.http.HttpStatus

enum class GlobalErrorCode(
    val status: HttpStatus,
    val errorCode: String,
    val message: String,
) {
    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-500", "서버 에러, 관리자에게 문의 바립니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON-400", "요청 형식이 잘못되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON-401", "인증 되지 않은 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON-403", "접근 권한이 없는 요청입니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON-404", "요청한 데이터를 찾을 수 없습니다."),

    // 뉴스 관련
    NEWS_ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "NEWS-ARTICLE-001", "뉴스 아티클이 존재하지 않습니다"),

    // 지자체 플로깅 관련
    PLOGGING_EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PLOGGING-EVENT-001", "플로깅 이벤트가 존재하지 않습니다"),
    PLOGGING_EVENT_FETCH_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "PLOGGING-EVENT-002",
        "지자체 플로깅 정보를 가져오던 중 예상치 못한 에러가 발생하였습니다.",
    ),

    // OAuth  관련
    OAUTH_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "OAUTH-001", "OAuth 서비스 측에서 에러가 발생했습니다."),
    OAUTH_UNEXPECTED_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "OAUTH-002",
        "OAuth 인증 과정에서 예상치 못한 에러가 발생하였습니다.",
    ),
    OAUTH_DUPLICATED_EMAIL(
        HttpStatus.CONFLICT,
        "OAUTH-003",
        "이미 해당 소셜 계정의 이메일로 가입한 이력이 있습니다. 기존에 가입한 소설 계정으로 로그인 해주세요.",
    ),

    // JWT 관련
    JWT_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-001", "유효하지 않는 토큰입니다."),
    JWT_MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-002", "잘못된 형식의 토큰입니다."),
    JWT_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-003", "유효기간이 만료된 토큰입니다."),
    JWT_UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-004", "지원하지 않는 형식의 토큰입니다."),

    // 유저 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-001", "유저가 존재하지 않습니다"),

    // 댓글 관련
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT-001", "댓글이 존재하지 않습니다"),
    COMMENT_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "COMMENT-002", "댓글에 대한 권한이 없습니다"),
    COMMENT_NOT_MATCH(HttpStatus.FORBIDDEN, "COMMENT-003", "댓글과 컨텐츠가 일치하지 않습니다"),
    COMMENT_DEPTH_EXCEEDED(HttpStatus.BAD_REQUEST, "COMMENT-004", "대댓글은 1단계까지만 작성 가능합니다"),

    // 플로깅 모임 관련
    PLOGGING_MEETUP_NOT_FOUND(HttpStatus.NOT_FOUND, "PLOGGING-MEETUP-001", "플로깅 모임이 존재하지 않습니다"),

    // 어드민 관련
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "ADMIN-001", "관리자가 존재하지 않습니다"),


    // Refresh Token 관련
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "REFRESH_TOKEN-001", "해당 아이디로 저장된 토큰이 없습니다."),
}
