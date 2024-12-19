package com.relogging.server.infrastructure.kakao.template

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTextMessageTemplate(
    @JsonProperty("object_type")
    val objectType: String,
    val text: String,
    val link: KakaoLinkTemplate,
)

data class KakaoLinkTemplate(
    @JsonProperty("web_url")
    val webUrl: String,
    @JsonProperty("mobile_web_url")
    val mobileWebUrl: String,
)
