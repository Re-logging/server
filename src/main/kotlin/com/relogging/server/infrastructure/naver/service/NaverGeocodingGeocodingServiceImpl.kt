package com.relogging.server.infrastructure.naver.service

import com.relogging.server.domain.utils.coordinate.Coordinate
import com.relogging.server.domain.utils.coordinate.dto.CoordinateConverter
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.infrastructure.naver.dto.NaverGeocodingResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class NaverGeocodingGeocodingServiceImpl(
    private val webClient: WebClient,
    @Value("\${naver.client-id}")
    private val clientId: String,
    @Value("\${naver.client-secret}")
    private val clientSecret: String,
) : NaverGeocodingService {
    companion object {
        private const val BASE_URL = "https://openapi.naver.com/v1/search/local.json"
    }

    override fun addressToCoordinate(address: String): Coordinate {
        val response =
            webClient
                .get()
                .uri("$BASE_URL?query=$address")
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono<NaverGeocodingResponse>()
                .block() ?: throw RuntimeException("Failed to get coordinate")

        if (response.total < 1) {
            throw GlobalException(GlobalErrorCode.NAVER_MAPS_ADDRESS_NOT_FOUND)
        }

        return CoordinateConverter.toEntity(response)
    }
}
