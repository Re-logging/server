package com.relogging.server.domain.image.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

@Service
class ImageServiceImpl : ImageService {
    override fun saveImageFile(
        file: MultipartFile,
        imageUploadDir: String,
    ): String {
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename
        val filePath = Paths.get(imageUploadDir, fileName)
        Files.copy(file.inputStream, filePath)
        return filePath.toString()
    }

    override fun saveImageFiles(
        imageList: List<MultipartFile>,
        imageUploadDir: String,
    ): List<String> {
        return imageList.map { file -> saveImageFile(file, imageUploadDir) }
    }
}
