package com.relogging.server.global

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import java.lang.reflect.Type

@Component
class MultipartJackson2HttpMessageConverter(
    objectMapper: ObjectMapper,
) : AbstractJackson2HttpMessageConverter(
        objectMapper,
        MediaType.APPLICATION_OCTET_STREAM,
    ) {
    /** HTTP 요청 변환기 supporting "Content-Type: multipart/form-data" header */
    override fun canWrite(
        clazz: Class<*>,
        mediaType: MediaType?,
    ): Boolean = false

    override fun canWrite(
        type: Type?,
        clazz: Class<*>,
        mediaType: MediaType?,
    ): Boolean = false

    override fun canWrite(mediaType: MediaType?): Boolean = false
}
