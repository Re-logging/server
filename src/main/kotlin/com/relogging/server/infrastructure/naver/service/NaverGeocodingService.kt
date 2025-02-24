package com.relogging.server.infrastructure.naver.service

import com.relogging.server.domain.utils.coordinate.Coordinate

interface NaverGeocodingService {
    fun addressToCoordinate(address: String): Coordinate
}
