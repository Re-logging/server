package com.relogging.server.global.exception

import org.springframework.http.HttpStatus

enum class GlobalErrorCode(
    val status: HttpStatus,
    val errorCode: String,
    val message: String,
) {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-001", "서버 에러, 관리자에게 문의 바립니다"),

    NEWS_ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "NEWS-ARTICLE-001", "뉴스 아티클이 존재하지 않습니다"),

    PLOGGING_EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PLOGGING-EVENT-001", "플로깅 이벤트가 존재하지 않습니다")
}
