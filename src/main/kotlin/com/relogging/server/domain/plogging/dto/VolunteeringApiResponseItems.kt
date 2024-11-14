package com.relogging.server.domain.plogging.dto

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringApiResponseItems(
    @field:XmlElement(name = "item")
    val item: List<VolunteeringListApiResponseItem>? = null,
)
