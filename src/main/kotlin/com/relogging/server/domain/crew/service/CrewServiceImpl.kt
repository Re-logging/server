package com.relogging.server.domain.crew.service

import com.relogging.server.domain.crew.dto.CrewConverter
import com.relogging.server.domain.crew.dto.CrewCreateRequest
import com.relogging.server.domain.crew.entity.CrewMember
import com.relogging.server.domain.crew.repository.CrewRepository
import com.relogging.server.domain.user.entity.User
import com.relogging.server.infrastructure.aws.s3.AmazonS3Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class CrewServiceImpl(
    private val crewRepository: CrewRepository,
    private val amazonS3Service: AmazonS3Service,
    @Value("\${cloud.aws.s3.path.crew}")
    private var imageUploadDir: String,
) : CrewService {
    @Transactional
    override fun createCrew(
        request: CrewCreateRequest,
        imageList: List<MultipartFile>,
        user: User,
    ): Long {
        val imagePathList = amazonS3Service.uploadFiles(imageList, imageUploadDir)
        val crew = CrewConverter.toEntity(request, imagePathList, user.name)
        val crewLeader = CrewMember.createCrewLeader(user = user, crew = crew)
        crew.addCrewMember(crewLeader)
        return crewRepository.save(crew).id!!
    }
}
