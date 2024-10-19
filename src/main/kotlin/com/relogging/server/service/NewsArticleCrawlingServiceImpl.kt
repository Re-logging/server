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
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd"),
) : CrawlingService {
    @Scheduled(cron = "0 0 3 * * *") // 매일 오전 3시에 작업 수행
    override fun crawlAndSaveNewsArticles() {
        val newsArticleList = mutableListOf<NewsArticle>()
        newsArticleList.addAll(crawlEconomyNews())
        newsArticleList.addAll(crawlESGEconomy())
        newsArticleService.saveNewsArticles(newsArticleList)
    }

    private fun crawlEconomyNews(): List<NewsArticle> {
        val baseUrl = "https://www.hkbs.co.kr/"
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
            val author = doc.select("div.account strong.name").text().trim()
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
                    content = content,
                    source = searchUrl,
                    author = author,
                    publishedAt = publishedAt,
                    aiSummary = null,
                )
            val imageUrl = doc.select("article#article-view-content-div img").attr("src").trim()
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
        return newsArticleList
    }

    private fun crawlESGEconomy(): List<NewsArticle> {
        val baseUrl = "https://www.esgeconomy.com/"
        val rootDoc = Jsoup.connect(baseUrl).get()
        val newsArticleList = mutableListOf<NewsArticle>()
        val existingTitles = newsArticleService.findAllTitles().toSet()

        for (page in 1..12) {
            val title = rootDoc.select("#skin-12 > div:nth-child($page) > div > a > strong").text().trim()
            if (title in existingTitles) {
                continue // 중복 크롤링 방지
            }
            val searchUrl = baseUrl + rootDoc.select("#skin-12 > div:nth-child($page) > div > a").attr("href")
            val doc = Jsoup.connect(searchUrl).get()
            val content = doc.select("#article-view-content-div").joinToString("\n") { it.text() }
            val author = doc.select("#article-view > div > header > div > article:nth-child(1) > ul > li:nth-child(1)").text().trim()

            println(
                doc
                    .select("#article-view > div > header > div > article:nth-child(1) > ul > li:nth-child(2)")
                    .text() +
                    "\n" + "$searchUrl",
            )

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
                    content = content,
                    source = searchUrl,
                    author = author,
                    publishedAt = publishedAt,
                    aiSummary = null,
                )
            val imageUrl = doc.select("#article-view-content-div > div:nth-child(1) > figure > div > img").attr("src").trim()
            val imageCaption = doc.select("#article-view-content-div > div:nth-child(1) > figure > div > img").attr("alt").trim()
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
        return newsArticleList
    }
}
