package com.relogging.server.domain.ploggingMeetup.service

import com.relogging.server.domain.image.service.ImageService
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupConverter
import com.relogging.server.domain.ploggingMeetup.dto.PloggingMeetupRequest
import com.relogging.server.domain.ploggingMeetup.repository.PloggingMeetupRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PloggingMeetupServiceImpl(
    private val ploggingMeetupRepository: PloggingMeetupRepository,
    private val imageService: ImageService,
    @Value("\${image-dir.plogging-meetup}")
    private var imageUploadDir: String,
) : PloggingMeetupService {
    override fun createMeetup(
        request: PloggingMeetupRequest,
        image: MultipartFile?,
    ): Long {
        val imageUrl = image?.let { imageService.saveImageFile(it, imageUploadDir) }
        val ploggingMeetup = PloggingMeetupConverter.toEntity(request, imageUrl)
        return ploggingMeetupRepository.save(ploggingMeetup).id!!
    }
}
