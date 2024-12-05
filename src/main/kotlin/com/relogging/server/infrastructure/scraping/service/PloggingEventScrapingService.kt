package com.relogging.server.infrastructure.scraping.service

interface PloggingEventScrapingService {
    fun scrapingPloggingEventImage(url: String): List<String>
}
