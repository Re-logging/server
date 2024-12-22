package com.relogging.server.infrastructure.scheduler

import com.relogging.server.domain.newsArticle.entity.NewsArticle
import com.relogging.server.domain.newsArticle.service.NewsArticleService
import com.relogging.server.infrastructure.openai.service.OpenAiService
import com.relogging.server.infrastructure.scraping.service.NewsArticleScrapingService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NewsArticleScrapingScheduler(
    private val newsArticleScrapingService: NewsArticleScrapingService,
    private val newsArticleService: NewsArticleService,
    private val aiService: OpenAiService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "0 0 3 * * *") // 매일 오전 3시에 작업 수행
    @Transactional
    fun scrapNewsArticlesDaily() {
        try {
            log.info("Starting daily news scraping...")
            val newsArticleList = mutableListOf<NewsArticle>()

            // 뉴스 스크래핑 수행
            newsArticleList.addAll(newsArticleScrapingService.scrapEconomyNews())
            newsArticleList.addAll(newsArticleScrapingService.scrapESGEconomy())

            // AI 요약 생성
            log.info("Generating AI summaries for ${newsArticleList.size} articles...")
            newsArticleList.map { it.aiSummary = aiService.aiSummary(it.content) }

            // DB 저장
            val savedCount = newsArticleService.saveNewsArticles(newsArticleList)
            log.info("Successfully scraped and saved $savedCount news articles")
        } catch (e: Exception) {
            log.error("Failed to scrap news articles: ${e.message}", e)
        }
    }
}
