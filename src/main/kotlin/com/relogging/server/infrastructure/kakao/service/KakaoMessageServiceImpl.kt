package com.relogging.server.infrastructure.kakao.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.relogging.server.infrastructure.kakao.template.KakaoLinkTemplate
import com.relogging.server.infrastructure.kakao.template.KakaoTextMessageTemplate
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient

@Service
class KakaoMessageServiceImpl(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper,
) : KakaoMessageService {
    override fun sendMemo(
        accessToken: String,
        message: String,
    ) {
        val templateObject = buildTextMessageTemplate(message)
        val formData =
            LinkedMultiValueMap<String, String>().apply {
                add("template_object", templateObject)
            }
        val url = "https://kapi.kakao.com/v2/api/talk/memo/default/send"

        val response =
            webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer $accessToken")
                .bodyValue(formData)
                .retrieve()
                .toBodilessEntity()
                .block() ?: throw RuntimeException("Failed to send Kakao memo")

        if (response.statusCode.isError) {
            throw RuntimeException("Failed to send memo: ${response.statusCode}: ${response.body}")
        }
    }

    private fun buildTextMessageTemplate(message: String): String {
        val template =
            KakaoTextMessageTemplate(
                objectType = "text",
                text = message,
                link =
                KakaoLinkTemplate(
                    webUrl = "https://www.re-logging.com/",
                    mobileWebUrl = "https://www.re-logging.com/",
                ),
            )
        return objectMapper.writeValueAsString(template)
    }
}
