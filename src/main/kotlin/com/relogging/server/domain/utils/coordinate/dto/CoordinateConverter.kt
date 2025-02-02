package com.relogging.server.domain.utils.coordinate.dto

import com.relogging.server.domain.utils.coordinate.Coordinate
import com.relogging.server.infrastructure.naver.dto.NaverGeocodingResponse

object CoordinateConverter {
    fun toResponse(coordinate: Coordinate?): CoordinateResponse =
        CoordinateResponse(
            lat = coordinate?.lat,
            lng = coordinate?.lng,
        )

    fun toEntity(request: CoordinateRequest): Coordinate =
        Coordinate(
            lat = request.lat,
            lng = request.lng,
        )

    fun toEntity(response: NaverGeocodingResponse): Coordinate =
        Coordinate(
            lat = response.items.first().mapy.toDouble() / 10000000,
            lng = response.items.first().mapx.toDouble() / 10000000,
        )
}
