package com.relogging.server.service.image

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

@Service
class ImageServiceImpl(
    @Value("\${image-dir.news-article}")
    private var uploadDir: String,
) : ImageService {
    @Transactional
    override fun saveImageFile(file: MultipartFile): String {
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename
        val filePath = Paths.get(uploadDir, fileName)
        Files.copy(file.inputStream, filePath)
        return filePath.toString()
    }
}
