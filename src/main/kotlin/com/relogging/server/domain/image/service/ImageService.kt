package com.relogging.server.domain.image.service

import org.springframework.web.multipart.MultipartFile

interface ImageService {
    fun saveImageFile(
        file: MultipartFile,
        imageUploadDir: String,
    ): String

    fun saveImageFiles(
        imageList: List<MultipartFile>,
        imageUploadDir: String,
    ): List<String>
}
