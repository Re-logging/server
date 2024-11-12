package com.relogging.server.domain.plogging.dto

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringApiResponse(
    @field:XmlElement(name = "body")
    val body: VolunteeringApiResponseBody? = null,
)
