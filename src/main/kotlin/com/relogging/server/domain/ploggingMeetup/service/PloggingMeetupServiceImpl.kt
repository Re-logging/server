package com.relogging.server.domain.ploggingMeetup.service

import com.relogging.server.domain.image.service.ImageService
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupConverter
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupListResponse
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupRequest
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupResponse
import com.relogging.server.domain.ploggingMeetup.repository.PloggingMeetupRepository
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PloggingMeetupServiceImpl(
    private val ploggingMeetupRepository: PloggingMeetupRepository,
    private val imageService: ImageService,
    @Value("\${image-dir.plogging-meetup}")
    private var imageUploadDir: String,
) : PloggingMeetupService {
    @Transactional
    override fun createMeetup(
        request: PloggingMeetupRequest,
        image: MultipartFile?,
    ): Long {
        val imageUrl = image?.let { imageService.saveImageFile(it, imageUploadDir) }
        val ploggingMeetup = PloggingMeetupConverter.toEntity(request, imageUrl)
        return ploggingMeetupRepository.save(ploggingMeetup).id!!
    }

    @Transactional
    override fun getMeetup(
        id: Long,
        increaseHits: Boolean,
    ): PloggingMeetupResponse {
        val meetup =
            ploggingMeetupRepository.findById(id).orElseThrow {
                throw GlobalException(GlobalErrorCode.PLOGGING_MEETUP_NOT_FOUND)
            }
        if (increaseHits) {
            meetup.hits += 1
        }
        return PloggingMeetupConverter.toResponse(meetup)
    }

    @Transactional
    override fun getNextMeetup(currentId: Long): PloggingMeetupResponse {
        val nextMeetup =
            ploggingMeetupRepository.findFirstByIdGreaterThanOrderByIdAsc(currentId)
                .orElseThrow {
                    throw GlobalException(GlobalErrorCode.PLOGGING_MEETUP_NOT_FOUND)
                }
        nextMeetup.hits += 1
        return PloggingMeetupConverter.toResponse(nextMeetup)
    }

    @Transactional
    override fun getPrevMeetup(currentId: Long): PloggingMeetupResponse {
        val prevMeetup =
            ploggingMeetupRepository.findFirstByIdLessThanOrderByIdDesc(currentId)
                .orElseThrow {
                    throw GlobalException(GlobalErrorCode.PLOGGING_MEETUP_NOT_FOUND)
                }
        prevMeetup.hits += 1
        return PloggingMeetupConverter.toResponse(prevMeetup)
    }

    @Transactional(readOnly = true)
    override fun getMeetupList(pageable: Pageable): PloggingMeetupListResponse {
        val response = ploggingMeetupRepository.findAll(pageable)
        return PloggingMeetupConverter.toResponse(response)
    }
}
