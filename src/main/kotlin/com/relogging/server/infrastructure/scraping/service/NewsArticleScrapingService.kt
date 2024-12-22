package com.relogging.server.infrastructure.scraping.service

import com.relogging.server.domain.newsArticle.entity.NewsArticle

interface NewsArticleScrapingService {
    fun scrapEconomyNews(): List<NewsArticle>

    fun scrapESGEconomy(): List<NewsArticle>
}
