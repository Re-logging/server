package com.relogging.server.domain.plogging.dto

import com.relogging.server.global.LocalDateAdapter
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringListApiResponseItem(
    // 봉사 등록 번호
    @field:XmlElement(name = "progrmRegistNo")
    val programRegistrationNumber: String? = null,
    // 봉사 제목
    @field:XmlElement(name = "progrmSj")
    val programSubject: String? = null,
    // 봉사 상태
    @field:XmlElement(name = "progrmSttusSe")
    val programStatus: String? = null,
    // 등록 기관
    @field:XmlElement(name = "nanmmbyNm")
    val registrationOrganization: String? = null,
    // 모집 시작일
    @field:XmlElement(name = "noticeBgnde")
    @field:XmlJavaTypeAdapter(LocalDateAdapter::class)
    val noticeBeginDate: LocalDate? = null,
    // 모집 종료일
    @field:XmlElement(name = "noticeEndde")
    @field:XmlJavaTypeAdapter(LocalDateAdapter::class)
    val noticeEndDate: LocalDate? = null,
    // 봉사 시작일
    @field:XmlElement(name = "progrmBgnde")
    @field:XmlJavaTypeAdapter(LocalDateAdapter::class)
    val programBeginDate: LocalDate? = null,
    // 봉사 종료일
    @field:XmlElement(name = "progrmEndde")
    @field:XmlJavaTypeAdapter(LocalDateAdapter::class)
    val programEndDate: LocalDate? = null,
    // 활동 시작 시간
    @field:XmlElement(name = "actBeginTm")
    val actBeginTime: Int? = null,
    // 활동 종료 시간
    @field:XmlElement(name = "actEndTm")
    val actEndTime: Int? = null,
    // 활동 장소
    @field:XmlElement(name = "actPlace")
    val actPlace: String? = null,
    // 성인 가능 여부(Y, N)
    @field:XmlElement(name = "adultPosblAt")
    val adultPossibleAt: String? = null,
    // 아동 청소년 가능 여부(Y, N)
    @field:XmlElement(name = "yngbgsPosblAt")
    val youngPossibleAt: String? = null,
    // 구, 군 코드
    @field:XmlElement(name = "gugunCd")
    val districtCode: String? = null,
    // 시, 도 코드
    @field:XmlElement(name = "sidoCd")
    val cityProvinceCode: String? = null,
    // 상위 - 하위 분야
    @field:XmlElement(name = "srvcClCode")
    val field: String? = null,
    // url
    @field:XmlElement(name = "url")
    val url: String? = null,
)
