package com.relogging.server.service.openai

import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.stereotype.Service

@Service
class OpenAiServiceImpl(
    private val chatModel: OpenAiChatModel,
) : OpenAiService {
    override fun aiSummary(content: String): String {
        if (content.length < 100) return content
        val prompt =
            """
            당신은 전문적인 뉴스 요약 AI입니다. 주어진 뉴스 기사를 다음 가이드라인에 따라 요약해주세요:
            1. 핵심 내용을 3-5문장으로 요약하세요.
            2. 중요한 이름, 날짜, 숫자를 포함하세요.
            3. 객관적이고 중립적인 톤을 유지하세요.
            4. 원문의 맥락을 유지하면서 간결하게 작성하세요.
            
            뉴스 기사:
            $content
            
            요약:
            """.trimIndent()
        return chatModel.call(prompt)
    }
}
