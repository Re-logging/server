package com.relogging.server.infrastructure.scraping.service

import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
class PloggingEventScrapingServiceImpl : PloggingEventScrapingService {
    override fun scrapingPloggingEventImage(url: String): List<String> {
        val doc = Jsoup.connect(url).get()
        val imgElements = doc.select("div.bb_txt img")
        val imgUrls =
            imgElements.mapNotNull { element ->
                element.attr("abs:src").takeIf { it.isNotEmpty() }
            }
        return imgUrls
    }
}
