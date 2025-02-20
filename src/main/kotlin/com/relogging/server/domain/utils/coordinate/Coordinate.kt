package com.relogging.server.domain.utils.coordinate

import jakarta.persistence.Embeddable

@Embeddable
class Coordinate(
    val lat: Double?,
    val lng: Double?,
)
