package com.relogging.server.service.newsArticle

import com.relogging.server.convertor.ImageConvertor
import com.relogging.server.convertor.NewsArticleConvertor
import com.relogging.server.dto.request.NewsArticleRequest
import com.relogging.server.dto.response.NewsArticleListResponse
import com.relogging.server.dto.response.NewsArticleResponse
import com.relogging.server.entity.NewsArticle
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.repository.NewsArticleRepository
import com.relogging.server.service.image.ImageService
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
) : NewsArticleService {
    @Transactional
    override fun createNewsArticle(
        request: NewsArticleRequest,
        imageList: List<MultipartFile>,
    ): NewsArticleResponse {
        val newsArticle = NewsArticleConvertor.toEntity(request)
        newsArticle.imageList =
            request.imageList.zip(imageList).map { (imageRequest, file) ->
                val savedFilePath = imageService.saveImageFile(file)
                ImageConvertor.toEntity(imageRequest, savedFilePath, newsArticle)
            }
        val savedArticle = newsArticleRepository.save(newsArticle)
        return NewsArticleConvertor.toResponse(savedArticle)
    }

    @Transactional(readOnly = true)
    override fun getNewsArticle(id: Long): NewsArticleResponse = NewsArticleConvertor.toResponse(getNewsArticleById(id))

    @Transactional(readOnly = true)
    override fun getNewsArticlePage(page: Int): NewsArticleListResponse {
        val pageable: Pageable = PageRequest.of(page, 9)
        val newsArticlePage: Page<NewsArticle> = newsArticleRepository.findAll(pageable)
        return NewsArticleConvertor.toResponse(newsArticlePage)
    }

    @Transactional
    override fun deleteNewsArticle(id: Long) = newsArticleRepository.delete(getNewsArticleById(id))

    private fun getNewsArticleById(id: Long): NewsArticle =
        newsArticleRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.NEWS_ARTICLE_NOT_FOUND)
        }
}
