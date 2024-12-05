package com.relogging.server.domain.newsArticle.service

import com.relogging.server.domain.image.dto.ImageConverter
import com.relogging.server.domain.newsArticle.dto.NewsArticleConverter
import com.relogging.server.domain.newsArticle.dto.NewsArticleListResponse
import com.relogging.server.domain.newsArticle.dto.NewsArticleRequest
import com.relogging.server.domain.newsArticle.dto.NewsArticleResponse
import com.relogging.server.domain.newsArticle.entity.NewsArticle
import com.relogging.server.domain.newsArticle.repository.NewsArticleRepository
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.infrastructure.aws.s3.AmazonS3Service
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
    private val amazonS3Service: AmazonS3Service,
    private val openAiService: OpenAiService,
    @Value("\${cloud.aws.s3.path.news-article}")
    private var imageUploadDir: String,
) : NewsArticleService {
    @Transactional
    override fun createNewsArticle(
        request: NewsArticleRequest,
        image: MultipartFile,
    ): NewsArticleResponse {
        val newsArticle = NewsArticleConverter.toEntity(request)
        val savedFilePath = amazonS3Service.uploadFile(image, imageUploadDir)
        newsArticle.imageList +=
            ImageConverter.toEntityWithNews(
                savedFilePath,
                request.imageCaption,
                newsArticle,
            )
        newsArticle.aiSummary = openAiService.aiSummary(newsArticle.content)
        val savedArticle = newsArticleRepository.save(newsArticle)
        return NewsArticleConverter.toResponse(savedArticle)
    }

    @Transactional
    override fun saveNewsArticles(newsArticles: List<NewsArticle>) {
        newsArticleRepository.saveAll(newsArticles)
    }

    @Transactional(readOnly = true)
    override fun findAllTitles(): List<String> = newsArticleRepository.findAllTitles()

    @Transactional
    override fun getNewsArticle(
        id: Long,
        increaseHits: Boolean,
    ): NewsArticleResponse {
        val newsArticle = getNewsArticleById(id)
        if (increaseHits) {
            newsArticle.hits++
        }
        return NewsArticleConverter.toResponse(newsArticle)
    }

    @Transactional
    override fun getPrevNewsArticle(id: Long): NewsArticleResponse {
        val newsArticle =
            newsArticleRepository.findPrevArticle(id).orElseThrow {
                throw GlobalException(GlobalErrorCode.NEWS_ARTICLE_NOT_FOUND)
            }
        newsArticle.hits++
        return NewsArticleConverter.toResponse(newsArticle)
    }

    @Transactional
    override fun getNextNewsArticle(id: Long): NewsArticleResponse {
        val newsArticle =
            newsArticleRepository.findNextArticle(id).orElseThrow {
                throw GlobalException(GlobalErrorCode.NEWS_ARTICLE_NOT_FOUND)
            }
        newsArticle.hits++
        return NewsArticleConverter.toResponse(newsArticle)
    }

    @Transactional(readOnly = true)
    override fun getNewsArticlePage(
        page: Int,
        pageSize: Int,
    ): NewsArticleListResponse {
        val pageable: Pageable = PageRequest.of(page, pageSize)
        val newsArticlePage: Page<NewsArticle> = newsArticleRepository.findAllByOrderByPublishedAtDescIdAsc(pageable)
        return NewsArticleConverter.toResponse(newsArticlePage)
    }

    @Transactional
    override fun deleteNewsArticle(id: Long) = newsArticleRepository.delete(getNewsArticleById(id))

    private fun getNewsArticleById(id: Long): NewsArticle =
        newsArticleRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.NEWS_ARTICLE_NOT_FOUND)
        }
}
