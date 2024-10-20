package com.relogging.server.service.crawling

interface CrawlingService {
    fun crawlAndSaveNewsArticles(): Int
}
