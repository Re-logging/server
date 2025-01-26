package com.relogging.server.domain.utils.coordinate.dto

import com.relogging.server.domain.utils.coordinate.Coordinate

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
}
