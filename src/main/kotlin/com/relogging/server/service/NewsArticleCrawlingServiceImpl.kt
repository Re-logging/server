package com.relogging.server.service

import com.relogging.server.entity.Image
import com.relogging.server.entity.NewsArticle
import com.relogging.server.service.newsArticle.NewsArticleService
import com.relogging.server.service.openai.OpenAiService
import org.jsoup.Jsoup
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class NewsArticleCrawlingServiceImpl(
    private val newsArticleService: NewsArticleService,
    private val aiService: OpenAiService,
    private val baseUrl: String = "https://www.hkbs.co.kr/",
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd"),
) : CrawlingService {
    @Scheduled(cron = "0 0 3 * * *") // 매일 오전 3시에 작업 수행
    override fun crawlAndSaveNewsArticles() {
        val rootDoc = Jsoup.connect(baseUrl).get()
        val newsArticleList = mutableListOf<NewsArticle>()
        val existingTitles = newsArticleService.findAllTitles().toSet()

        for (page in 1..10) {
            val title = rootDoc.select("#skin-15 > div:nth-child($page) > a > strong").text().trim()
            if (title in existingTitles) {
                continue // 중복 크롤링 방지
            }

            val searchUrl = baseUrl + rootDoc.select("#skin-15 > div:nth-child($page) > a").attr("href")
            val doc = Jsoup.connect(searchUrl).get()

            val content = doc.select("article#article-view-content-div p").joinToString("\n") { it.text() }
            val author = doc.select("div.account strong.name").text()
            val publishedAt =
                LocalDate.parse(
                    doc
                        .select("#article-view > div > header > div > article:nth-child(1) > ul > li:nth-child(2)")
                        .text()
                        .substring(3, 13),
                    dateFormatter,
                )
            val newsArticle =
                NewsArticle(
                    title = title,
                    content = content + '\n' + publishedAt,
                    source = searchUrl,
                    author = author,
                    publishedAt = publishedAt,
                    aiSummary = null,
                )
            val imageUrl = doc.select("article#article-view-content-div img").attr("src").trimIndent()
            val imageCaption = doc.select("article#article-view-content-div img").attr("alt").trim()
            if (imageUrl.isNotEmpty()) {
                val image =
                    Image(
                        url = imageUrl,
                        caption = imageCaption,
                        orderIndex = 0,
                        newsArticle = newsArticle,
                    )
                newsArticle.imageList = listOf(image)
            }
            newsArticle.aiSummary = aiService.aiSummary(newsArticle.content)
            newsArticleList.add(newsArticle)
        }
        newsArticleService.saveNewsArticles(newsArticleList)
    }
}
