package com.relogging.server.service.image

import org.springframework.web.multipart.MultipartFile

interface ImageService {
    fun saveImageFile(file: MultipartFile): String
}
