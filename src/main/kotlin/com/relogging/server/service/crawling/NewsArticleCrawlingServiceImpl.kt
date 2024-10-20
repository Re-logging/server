package com.relogging.server.service.crawling

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
    override fun crawlAndSaveNewsArticles(): Int {
        val newsArticleList = mutableListOf<NewsArticle>()
        newsArticleList.addAll(crawlEconomyNews())
        newsArticleList.addAll(crawlESGEconomy())
        newsArticleList.map { it.aiSummary = aiService.aiSummary(it.content) }
        newsArticleService.saveNewsArticles(newsArticleList)
        return newsArticleList.size
    }

    private fun crawlEconomyNews(): List<NewsArticle> =
        crawlNewsArticle(
            baseUrl = "https://www.hkbs.co.kr/",
            existingTitles = newsArticleService.findAllTitles().toSet(),
            pageSize = 9,
            titleSelector = "#skin-15 > div:nth-child(\$page) > a > strong",
            searchUrlSelector = "#skin-15 > div:nth-child(\$page) > a",
            contentSelector = "article#article-view-content-div p",
            authorSelector = "div.account strong.name",
            publishedSelector = "#article-view > div > header > div > article:nth-child(1) > ul > li:nth-child(2)",
            imageSelector = "article#article-view-content-div img",
            imageCaptionSelector = "article#article-view-content-div img",
        )

    private fun crawlESGEconomy(): List<NewsArticle> =
        crawlNewsArticle(
            baseUrl = "https://www.esgeconomy.com/",
            existingTitles = newsArticleService.findAllTitles().toSet(),
            pageSize = 12,
            titleSelector = "#skin-12 > div:nth-child(\$page) > div > a > strong",
            searchUrlSelector = "#skin-12 > div:nth-child(\$page) > div > a",
            contentSelector = "#article-view-content-div p",
            authorSelector = "#article-view > div > header > div > article:nth-child(1) > ul > li:nth-child(1)",
            publishedSelector = "#article-view > div > header > div > article:nth-child(1) > ul > li:nth-child(2)",
            imageSelector = "#article-view-content-div > div:nth-child(1) > figure > div > img",
            imageCaptionSelector = "#article-view-content-div > div:nth-child(1) > figure > div > img",
        )

    private fun crawlNewsArticle(
        baseUrl: String,
        existingTitles: Set<String>,
        pageSize: Int,
        titleSelector: String,
        searchUrlSelector: String,
        contentSelector: String,
        authorSelector: String,
        publishedSelector: String,
        imageSelector: String,
        imageCaptionSelector: String,
    ): List<NewsArticle> {
        val rootDoc = Jsoup.connect(baseUrl).get()
        val newsArticleList = mutableListOf<NewsArticle>()

        for (page in 1..pageSize) {
            val title = rootDoc.select(titleSelector.replace("\$page", "$page")).text().trim()
            if (title in existingTitles) {
                continue // 중복 크롤링 방지
            }
            val searchUrl =
                baseUrl + rootDoc.select(searchUrlSelector.replace("\$page", "$page")).attr("href")
            val doc = Jsoup.connect(searchUrl).get()
            val content = doc.select(contentSelector).joinToString("\n") { it.text() }
            val author = doc.select(authorSelector).text().trim()
            val publishedAt =
                LocalDate.parse(
                    doc
                        .select(publishedSelector)
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
            val imageUrl = doc.select(imageSelector).attr("src").trim()
            val imageCaption = doc.select(imageCaptionSelector).attr("alt").trim()
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
            newsArticleList.add(newsArticle)
        }
        return newsArticleList
    }
}
