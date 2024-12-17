package com.relogging.server.infrastructure.kakao.service

interface KakaoMessageService {
    fun sendMemo(
        accessToken: String,
        message: String,
    )
}
