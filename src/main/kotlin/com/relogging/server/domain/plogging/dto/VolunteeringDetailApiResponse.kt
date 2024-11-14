package com.relogging.server.domain.plogging.dto

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringDetailApiResponse(
    @field:XmlElement(name = "body")
    val body: VolunteeringDetailApiResponseBody? = null,
)

@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringDetailApiResponseBody(
    @field:XmlElement(name = "items")
    val items: VolunteeringDetailApiResponseItems? = null,
    @field:XmlElement(name = "totalCount")
    val totalCount: Int? = null,
)

@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringDetailApiResponseItems(
    @field:XmlElement(name = "item")
    val item: List<VolunteeringDetailApiResponseItem>? = null,
)
