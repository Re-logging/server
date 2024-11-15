package com.relogging.server.global

import jakarta.xml.bind.annotation.adapters.XmlAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter : XmlAdapter<String, LocalDate>() {
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    override fun unmarshal(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, formatter) }
    }

    override fun marshal(value: LocalDate?): String? {
        return value?.format(formatter)
    }
}
