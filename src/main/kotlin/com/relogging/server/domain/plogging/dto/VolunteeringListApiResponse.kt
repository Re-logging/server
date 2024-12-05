package com.relogging.server.domain.plogging.dto

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringListApiResponse(
    @field:XmlElement(name = "body")
    val body: VolunteeringListApiResponseBody? = null,
)

@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringListApiResponseBody(
    @field:XmlElement(name = "items")
    val items: VolunteeringListApiResponseItems? = null,
    @field:XmlElement(name = "totalCount")
    val totalCount: Int? = null,
)

@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringListApiResponseItems(
    @field:XmlElement(name = "item")
    val item: List<VolunteeringListApiResponseItem>? = null,
)
