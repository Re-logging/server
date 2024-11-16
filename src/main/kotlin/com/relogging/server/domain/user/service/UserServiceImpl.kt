package com.relogging.server.domain.user.service

import com.relogging.server.domain.image.dto.ImageConverter
import com.relogging.server.domain.user.entity.SocialType
import com.relogging.server.domain.user.entity.User
import com.relogging.server.domain.user.repository.UserRepository
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.infrastructure.aws.s3.AmazonS3Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val amazonS3Service: AmazonS3Service,
    @Value("\${cloud.aws.s3.path.user}")
    private var imageUploadDir: String,
) : UserService {
    @Transactional(readOnly = true)
    override fun findUserByEmail(email: String): User? {
        return this.userRepository.findByEmail(email).orElse(null)
    }

    @Transactional(readOnly = true)
    override fun getUserById(id: Long): User {
        return this.userRepository.findById(id).orElseThrow {
            GlobalException(GlobalErrorCode.USER_NOT_FOUND)
        }
    }

    @Transactional
    override fun createUserWithEssentialInfo(
        name: String,
        email: String,
        nickName: String,
        socialType: SocialType,
        providerId: String,
    ): User {
        return this.userRepository.save(
            User(
                name = name,
                email = email,
                nickname = nickName,
                socialType = socialType,
                providerId = providerId,
            ),
        )
    }

    override fun getUser(id: Long): User =
        userRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.USER_NOT_FOUND)
        }

    @Transactional
    override fun updateAccountInfo(
        id: Long,
        name: String,
    ): User {
        val user = this.getUserById(id)
        user.name = name

        return user
    }

    @Transactional
    override fun updateProfileInfo(
        id: Long,
        nickname: String,
        image: MultipartFile?,
    ): User {
        val user = this.getUserById(id)
        user.nickname = nickname

        if (image != null) {
            val newImageUrl =
                this.amazonS3Service.updateFile(user.profileImage?.url, image, this.imageUploadDir)
            user.profileImage = ImageConverter.toEntity(newImageUrl)
        }

        return user
    }

    override fun deleteUser(id: Long) {
        this.userRepository.deleteById(id)
    }
}
