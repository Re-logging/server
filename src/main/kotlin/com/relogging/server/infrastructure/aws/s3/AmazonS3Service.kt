package com.relogging.server.infrastructure.aws.s3

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream
import java.util.UUID

@Service
class AmazonS3Service(
    private val amazonS3Client: AmazonS3Client,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,
) {
    fun uploadFile(
        file: MultipartFile,
        uploadDir: String,
    ): String {
        val originalFileName: String? = file.originalFilename

        if (!isImageFile(originalFileName as String)) {
            throw IllegalArgumentException("png, jpeg, jpg에 해당하는 파일만 업로드할 수 있습니다.")
        }

        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = file.size
        objectMetadata.contentType = file.contentType

        var uploadFileUrl = ""

        try {
            val inputStream: InputStream = file.inputStream
            val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename
            amazonS3Client.putObject(bucket, fileName, inputStream, objectMetadata)
            amazonS3Client.getUrl(bucket, originalFileName).toString()
        } catch (e: IOException) {
            throw GlobalException(GlobalErrorCode.BAD_REQUEST)
            e.printStackTrace()
        }
//        try {
//            amazonS3.putObject(
//                new PutObjectRequest(
//                        amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
//        } catch (IOException e) {
//            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
//            throw new GlobalException(GlobalErrorCode._BAD_REQUEST);
//        }
        return amazonS3Client.getUrl(bucket, originalFileName).toString()
    }

    fun uploadFiles(
        files: List<MultipartFile>,
        uploadDir: String,
    ): List<String> = files.map { uploadFile(it, uploadDir) }

    private fun isImageFile(fileName: String): Boolean {
        val allowedExtensions = listOf("png", "jpg", "jpeg")
        val extension = fileName.substringAfterLast(".", "")
        return extension.lowercase() in allowedExtensions
    }
}
