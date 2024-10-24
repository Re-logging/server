package com.relogging.server.domain.image.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

@Service
class ImageServiceImpl : ImageService {
    @Transactional
    override fun saveImageFile(
        file: MultipartFile,
        savePath: String,
    ): String {
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename
        val filePath = Paths.get(savePath, fileName)
        Files.copy(file.inputStream, filePath)
        return filePath.toString()
    }
}
