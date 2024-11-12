package com.relogging.server.domain.plogging.dto

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringApiResponseBody(
    @field:XmlElement(name = "items")
    val items: VolunteeringApiResponseItems? = null,
    @field:XmlElement(name = "totalCount")
    val totalCount: Int? = null,
)
