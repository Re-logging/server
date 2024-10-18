package com.relogging.server.service.openai

interface OpenAiService {
    fun aiSummary(content: String): String
}
