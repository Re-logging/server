package com.relogging.server.domain.image.service

import org.springframework.web.multipart.MultipartFile

interface ImageService {
    fun saveImageFile(
        file: MultipartFile,
        savePath: String,
    ): String
}
