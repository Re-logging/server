package com.relogging.server.domain.newsArticle.service

import com.relogging.server.domain.image.dto.ImageConvertor
import com.relogging.server.domain.image.service.ImageService
import com.relogging.server.domain.newsArticle.dto.NewsArticleConvertor
import com.relogging.server.domain.newsArticle.dto.NewsArticleListResponse
import com.relogging.server.domain.newsArticle.dto.NewsArticleRequest
import com.relogging.server.domain.newsArticle.dto.NewsArticleResponse
import com.relogging.server.domain.newsArticle.entity.NewsArticle
import com.relogging.server.domain.newsArticle.repository.NewsArticleRepository
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.infrastructure.openai.service.OpenAiService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class NewsArticleServiceImpl(
    private val newsArticleRepository: NewsArticleRepository,
    private val imageService: ImageService,
    private val openAiService: OpenAiService,
    @Value("\${image-dir.news-article}")
    private var imageUploadDir: String,
) : NewsArticleService {
    @Transactional
    override fun createNewsArticle(
        request: NewsArticleRequest,
        image: MultipartFile,
    ): NewsArticleResponse {
        val newsArticle = NewsArticleConvertor.toEntity(request)
        val savedFilePath = imageService.saveImageFile(image, imageUploadDir)
        newsArticle.imageList +=
            ImageConvertor.toEntityWithNews(
                savedFilePath,
                request.imageCaption,
                newsArticle,
            )
        newsArticle.aiSummary = openAiService.aiSummary(newsArticle.content)
        val savedArticle = newsArticleRepository.save(newsArticle)
        return NewsArticleConvertor.toResponse(savedArticle)
    }

    @Transactional
    override fun saveNewsArticles(newsArticles: List<NewsArticle>) {
        newsArticleRepository.saveAll(newsArticles)
    }

    @Transactional(readOnly = true)
    override fun findAllTitles(): List<String> = newsArticleRepository.findAllTitles()

    @Transactional(readOnly = true)
    override fun getNewsArticle(id: Long): NewsArticleResponse = NewsArticleConvertor.toResponse(getNewsArticleById(id))

    @Transactional(readOnly = true)
    override fun getPrevNewsArticle(id: Long): NewsArticleResponse =
        NewsArticleConvertor.toResponse(
            newsArticleRepository.findPrevArticle(id).orElseThrow {
                throw GlobalException(GlobalErrorCode.NEWS_ARTICLE_NOT_FOUND)
            },
        )

    @Transactional(readOnly = true)
    override fun getNextNewsArticle(id: Long): NewsArticleResponse =
        NewsArticleConvertor.toResponse(
            newsArticleRepository.findNextArticle(id).orElseThrow {
                throw GlobalException(GlobalErrorCode.NEWS_ARTICLE_NOT_FOUND)
            },
        )

    @Transactional(readOnly = true)
    override fun getNewsArticlePage(
        page: Int,
        pageSize: Int,
    ): NewsArticleListResponse {
        val pageable: Pageable = PageRequest.of(page, pageSize)
        val newsArticlePage: Page<NewsArticle> = newsArticleRepository.findAllByOrderByPublishedAtDescIdAsc(pageable)
        return NewsArticleConvertor.toResponse(newsArticlePage)
    }

    @Transactional
    override fun deleteNewsArticle(id: Long) = newsArticleRepository.delete(getNewsArticleById(id))

    private fun getNewsArticleById(id: Long): NewsArticle =
        newsArticleRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.NEWS_ARTICLE_NOT_FOUND)
        }
}
