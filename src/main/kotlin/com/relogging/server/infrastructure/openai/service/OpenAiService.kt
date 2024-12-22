package com.relogging.server.infrastructure.openai.service

interface OpenAiService {
    fun aiSummary(content: String): String
}
